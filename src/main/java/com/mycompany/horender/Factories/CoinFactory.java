package com.mycompany.horender.Factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.mycompany.horender.Components.CoinComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CoinFactory {
    public Entity createCoin(SpawnData data) {
        Circle circle = new Circle(10, Color.GOLD);
        circle.setStroke(Color.ORANGE);
        return FXGL.entityBuilder(data)
                .view(circle)
                .with(new CoinComponent(1))
                .build();
    }
}