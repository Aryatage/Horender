package com.mycompany.horender.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.mycompany.horender.Controllers.UIManager;
import com.mycompany.horender.EntitiesType;
import com.mycompany.horender.Views.PlayerView;
import javafx.scene.input.KeyCode;

public class CombatComponent extends Component {

    public enum AttackType { HIGH, MID, LOW }

    private enum State { IDLE, MONSTER_ATTACK_WARNING, PLAYER_EVADE, PLAYER_ATTACK_WINDOW }
    private State state = State.IDLE;
    private double lastPlayerAttackTime = 0;
    private static final double PLAYER_ATTACK_COOLDOWN = 0.3; // segundos
    private Entity monster;
    private HealthComponent monsterHealth;
    private HealthComponent playerHealth;

    private AttackType currentMonsterAttack;
    private boolean playerEvadedCorrect = false;

    private double stateTimer = 0;
    private static final double WARNING_DURATION = 0.6;
    private static final double EVADE_TIMEOUT = 3.0;
    private static final double ATTACK_WINDOW = 1.5;
    private static final double POST_ATTACK_DURATION = 0.8;

    public static boolean combatActive = false;
    private PlayerView playerView;

    @Override
    public void onAdded() {
        playerView = entity.getComponent(PlayerView.class);
        playerHealth = entity.getComponent(HealthComponent.class);
    }
    
    public void onUpdate(double tpf) {
    if (playerHealth.isDead()) return;

    // Localiza monstro
    if (monster == null || !monster.isActive()) {
        monster = FXGL.getGameWorld()
                .getEntitiesByType(EntitiesType.MONSTER01)
                .stream().findFirst().orElse(null);
        if (monster != null) {
            monsterHealth = monster.getComponent(HealthComponent.class);
        }
    }

    // Se não há monstro ou está morto, finaliza combate
    if (monster == null || !monster.isActive()) {
        state = State.IDLE;
        combatActive = false;
        return;
    }

    if (monsterHealth == null) {
        // Componente ainda não disponível – tenta novamente no próximo frame
        return;
    }

    if (monsterHealth.isDead()) {
        endCombatRound();  // garante que o round seja encerrado e moedas geradas
        return;
    }        
        switch (state) {
            case IDLE:
                MonsterComponent ai = monster.getComponent(MonsterComponent.class);
                if (ai != null && ai.isReadyToAttack(getEntity())) {
                    startMonsterAttack(ai);
                }
                break;

            case MONSTER_ATTACK_WARNING:
                stateTimer -= tpf;
                if (stateTimer <= 0) {
                    UIManager.hideCombatMessage();
                    state = State.PLAYER_EVADE;
                    stateTimer = EVADE_TIMEOUT;
                    UIManager.showCombatMessage("Escolha a esquiva!\nCIMA: alta  BAIXO: baixa  DIREITA: trás");
                }
                break;

            case PLAYER_EVADE:
                stateTimer -= tpf;
                if (stateTimer <= 0) {
                    applyDamageToPlayer();
                }
                break;

            case PLAYER_ATTACK_WINDOW:
                stateTimer -= tpf;
                if (stateTimer <= 0) {
                    endCombatRound();
                }
                break;
        }
    }

    public void playerAttack() {
    int damage = UIManager.hasDamageBoost() ? 35 : 20;
    monsterHealth.takeDamage(damage);
    if (state != State.PLAYER_ATTACK_WINDOW) return;
    double now = FXGL.getGameTimer().getNow();
    if (now - lastPlayerAttackTime < PLAYER_ATTACK_COOLDOWN) return;

    if (monsterHealth != null) {
        monsterHealth.takeDamage(20);
    }
    // Toca a animação de ataque (sempre reinicia)
    if (playerView != null) {
        playerView.playAttack();
    }
    lastPlayerAttackTime = now;
    // A janela continua ativa – não muda de estado
}

    private void startMonsterAttack(MonsterComponent ai) {
        combatActive = true;
        lastPlayerAttackTime = 0;                     // libera o primeiro ataque imediatamente
        currentMonsterAttack = ai.consumeAttack();
        String attackName = switch (currentMonsterAttack) {
            case HIGH -> "Ataque Alto!";
            case MID -> "Ataque Médio!";
            case LOW -> "Ataque Baixo!";
        };
        UIManager.showCombatMessage(attackName);
        state = State.MONSTER_ATTACK_WARNING;
        stateTimer = WARNING_DURATION;
    }

    public void playerEvade(KeyCode direction) {
        if (state != State.PLAYER_EVADE) return;

        AttackType evasion = switch (direction) {
            case UP -> AttackType.HIGH;
            case DOWN -> AttackType.LOW;
            case LEFT, RIGHT -> AttackType.MID;
            default -> null;
        };
        if (evasion == null) return;

        playerEvadedCorrect = (currentMonsterAttack == AttackType.HIGH && evasion == AttackType.LOW) ||
                              (currentMonsterAttack == AttackType.LOW && evasion == AttackType.HIGH) ||
                              (currentMonsterAttack == AttackType.MID && evasion == AttackType.MID);

        if (playerEvadedCorrect) {
            UIManager.showCombatMessage("Esquivou! Ataque com J ou ESPAÇO");
            state = State.PLAYER_ATTACK_WINDOW;
            stateTimer = ATTACK_WINDOW;
        } else {
            applyDamageToPlayer();
        }
    }

    private void applyDamageToPlayer() {
        if (playerHealth != null) {
            playerHealth.takeDamage(15);
        }
        endCombatRound();
    }

    private void endCombatRound() {
        UIManager.hideCombatMessage();
        state = State.IDLE;
        combatActive = false;
        playerEvadedCorrect = false;
        if (playerView != null) {
            playerView.playIdle();
        }
        if (monsterHealth != null && monsterHealth.isDead()) {
            FXGL.spawn("coin", monster.getX(), monster.getY() - 20);
            UIManager.addCoins(1);
        }
    }

    public static boolean isCombatActive() {
        return combatActive;
    }
}