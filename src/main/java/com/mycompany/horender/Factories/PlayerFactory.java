/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Factories;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.FXGL;
import com.mycompany.horender.Components.PlayerComponent;
import View.PlayerView;
import static com.mycompany.horender.EntitiesType.PLAYER;

public class PlayerFactory {

    public Entity createPlayer(SpawnData data) {
        PlayerView view = new PlayerView();
        PlayerComponent playerComp = new PlayerComponent(); // sem parâmetros

        return FXGL.entityBuilder(data)
                .type(PLAYER)
                .with(view)          // Adiciona a View
                .with(playerComp)    // Adiciona o Component
                .build();
    }
}