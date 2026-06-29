package com.mycompany.horender.Factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.mycompany.horender.EntitiesType;
import com.mycompany.horender.Components.HealthComponent;
import com.mycompany.horender.Components.MonsterComponent;
import com.mycompany.horender.Views.MonsterView;

public class MonsterFactory {

    public Entity createMonster(SpawnData data) {
        MonsterView view = new MonsterView();
        HealthComponent health = new HealthComponent(40);
        MonsterComponent ai = new MonsterComponent();

        return FXGL.entityBuilder(data)
                .type(EntitiesType.MONSTER01)
                .with(view)
                .with(health)
                .with(ai)
                .build();
    }
}