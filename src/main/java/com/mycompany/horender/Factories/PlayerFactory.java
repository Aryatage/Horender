/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Factories;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.FXGL;
import com.mycompany.horender.Components.CameraFollowComponent;
import com.mycompany.horender.Components.CombatComponent;
import com.mycompany.horender.Components.HealthComponent;
import com.mycompany.horender.Components.HealthDisplayComponent;
import com.mycompany.horender.Components.PlayerComponent;
import com.mycompany.horender.Components.PlayerDeathComponent;
import com.mycompany.horender.Components.PlayerMovementComponent;
import com.mycompany.horender.Controllers.UIManager;
import com.mycompany.horender.EntitiesType;
import com.mycompany.horender.Views.PlayerView;
import static com.mycompany.horender.EntitiesType.PLAYER;

public class PlayerFactory {

 public Entity createPlayer(SpawnData data) {
    PlayerDeathComponent death = new PlayerDeathComponent();
    PlayerView view = new PlayerView();
    PlayerComponent playerComp = new PlayerComponent();
    CombatComponent combat = new CombatComponent();
    PlayerMovementComponent movement = new PlayerMovementComponent();
    int maxHealth = UIManager.hasHealthBoost() ? 150 : 100;
    HealthComponent health;
     health = new HealthComponent(maxHealth);
    
    return FXGL.entityBuilder(data)
        .type(EntitiesType.PLAYER)
        .with(view)
        .with(playerComp)
        .with(health)
        .with(combat)
        .with(movement)
        .with(death)   // ← adiciona o novo componente
        .with(new CameraFollowComponent())
        .build();
    }  
}