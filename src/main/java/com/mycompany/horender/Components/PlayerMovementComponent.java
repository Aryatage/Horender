package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;
import com.mycompany.horender.Controllers.UIManager;
import com.mycompany.horender.HorenderApp;
import com.mycompany.horender.Views.PlayerView;

public class PlayerMovementComponent extends Component {

    private static final double SPEED = 200.0;
    private PlayerView playerView;
    private String lastAnim = "idle";

    @Override
    public void onUpdate(double tpf) {
        // Pausa movimento durante combate ativo
        if (CombatComponent.combatActive) return;

        // Obtém referência da view sob demanda
        if (playerView == null) {
            playerView = entity.getComponent(PlayerView.class);
        }

        int direction = 0;
        if (HorenderApp.moveLeft && !HorenderApp.moveRight) direction = -1;
        else if (HorenderApp.moveRight && !HorenderApp.moveLeft) direction = 1;

        if (direction != 0) {
            entity.translateX(direction * SPEED * tpf);
            if (playerView != null) playerView.setFlipped(direction < 0);
        }

        // Animação (idle / walk)
        if (direction != 0) {
            if (!lastAnim.equals("walk")) {
                if (playerView != null) playerView.playWalk();
                lastAnim = "walk";
            }
        } else {
            if (!lastAnim.equals("idle")) {
                if (playerView != null) playerView.playIdle();
                lastAnim = "idle";
            }
        }

        // Atualiza HUD de vida
        HealthComponent health = entity.getComponent(HealthComponent.class);
        if (health != null) {
            UIManager.updatePlayerHealthText(health.getCurrentHealth(), health.getMaxHealth());
        }
        // Limites do mundo
        double minX = -200;
        double maxX = 2048;   // 1664
        if (entity.getX() < minX) entity.setX(minX);
        if (entity.getX() > maxX) entity.setX(maxX);
    }

    public boolean isMoving() {
        return HorenderApp.moveLeft || HorenderApp.moveRight;
    }
}