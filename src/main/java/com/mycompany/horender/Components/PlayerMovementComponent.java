/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Components;

/**
 *
 * @author Felipe, Henrique
 */
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.mycompany.horender.Views.PlayerView;
import javafx.scene.input.KeyCode;

public class PlayerMovementComponent extends Component {

    private static final double SPEED = 200.0;   // pixels por segundo
    private PlayerView playerView;

    @Override
    public void onAdded() {
        playerView = entity.getComponent(PlayerView.class);
    }

    @Override
public void onUpdate(double tpf) {
    boolean left = FXGL.getInput().isHeld(KeyCode.A) || FXGL.getInput().isHeld(KeyCode.LEFT);
    boolean right = FXGL.getInput().isHeld(KeyCode.D) || FXGL.getInput().isHeld(KeyCode.RIGHT);

    int direction = 0;
    if (left && !right) direction = -1;
    else if (right && !left) direction = 1;

    if (direction != 0) {
        entity.translateX(direction * SPEED * tpf);
        if (playerView != null) {
            playerView.setFlipped(direction < 0);
            playerView.playWalk();    // ← animação de caminhada
        }
    } else {
        if (playerView != null) {
            playerView.playIdle();    // ← volta para idle
        }
    }
    }
}