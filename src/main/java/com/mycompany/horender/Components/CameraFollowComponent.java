package com.mycompany.horender.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class CameraFollowComponent extends Component {

    @Override
    public void onUpdate(double tpf) {
        double appWidth = FXGL.getAppWidth();
        double worldWidth = 2048;  // mesma largura do cenário
        double targetX = entity.getX() - appWidth * -0.005;  // jogador à esquerda

        // Clamp para não ultrapassar os limites do mundo
        targetX = Math.max(0, Math.min(targetX, worldWidth - appWidth));

        FXGL.getGameScene().getViewport().setX(targetX);
        FXGL.getGameScene().getViewport().setY(0);  // fixa topo
    }
}