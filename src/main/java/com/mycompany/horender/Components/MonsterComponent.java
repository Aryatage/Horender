package com.mycompany.horender.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.mycompany.horender.Components.CombatComponent.AttackType;
import com.mycompany.horender.Controllers.UIManager;
import com.mycompany.horender.EntitiesType;
import com.mycompany.horender.Views.MonsterView;
import java.util.Random;

public class MonsterComponent extends Component {

    private static final double MOVE_SPEED = 120.0;
    private static final double ATTACK_RANGE = 60.0;
    private static final double ATTACK_COOLDOWN = 2.0;

    private HealthComponent health;
    private MonsterView monsterView;
    private Entity player;
    private double lastAttackTime;
    private boolean hasPendingAttack = false;
    private AttackType pendingAttackType;
    private Random rand = new Random();

   @Override
    public void onAdded() {
    health = entity.getComponent(HealthComponent.class);
    monsterView = entity.getComponent(MonsterView.class);
    lastAttackTime = FXGL.getGameTimer().getNow();  // aguarda o cooldown antes do primeiro ataque
    }

    @Override
    public void onUpdate(double tpf) {
        if (!UIManager.isGameplayActive()) return;
        if (health.isDead()) {
            entity.removeFromWorld();
            return;
        }
        if (CombatComponent.combatActive) return;
        if (UIManager.hasInvisibility()) return;   // monstro não faz nada

        if (player == null || !player.isActive()) {
            player = FXGL.getGameWorld()
                    .getEntitiesByType(EntitiesType.PLAYER)
                    .stream().findFirst().orElse(null);
            if (player == null) return;
        }

        double dx = player.getX() - entity.getX();
        double distance = Math.abs(dx);

        if (distance > ATTACK_RANGE) {
            double direction = Math.signum(dx);
            entity.translateX(direction * MOVE_SPEED * tpf);
            // Espelhamento: sprite original olha para esquerda → flip se mover para direita
            if (monsterView != null) {
                monsterView.setFlipped(direction > 0);
            }
        }

        double now = FXGL.getGameTimer().getNow();
        if (distance <= ATTACK_RANGE && now - lastAttackTime >= ATTACK_COOLDOWN) {
            hasPendingAttack = true;
            int r = rand.nextInt(3);
            pendingAttackType = switch (r) {
                case 0 -> AttackType.HIGH;
                case 1 -> AttackType.MID;
                default -> AttackType.LOW;
            };
            lastAttackTime = now;
        }
    }

    public boolean isReadyToAttack(Entity playerEntity) {
        if (!hasPendingAttack) return false;
        HealthComponent playerHealth = playerEntity.getComponent(HealthComponent.class);
        return playerHealth != null && !playerHealth.isDead();
    }

    public AttackType consumeAttack() {
        hasPendingAttack = false;
        return pendingAttackType;
    }
}