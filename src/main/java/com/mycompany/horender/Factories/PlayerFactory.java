/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Factories;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.mycompany.horender.Components.AnimationsComponent;
import com.mycompany.horender.Components.PlayerComponent;
import static com.mycompany.horender.EntitiesType.PLAYER;

public class PlayerFactory {

    public Entity createPlayer(SpawnData data) {
        // 1. Cria a animação
        AnimationsComponent animComp = new AnimationsComponent();

        // 2. Cria o componente do jogador (sem parâmetros)
        PlayerComponent playerComp = new PlayerComponent();

        // 3. Constrói a entidade com ambos
        return FXGL.entityBuilder(data)
                .type(PLAYER)
                .with(animComp)
                .with(playerComp)
                .build();
    }
}
/*public class PlayerFactory {

    public Entity createPlayer(SpawnData data) {
        // 1. Criamos a peça de animação
        AnimationsComponent animComp = new AnimationsComponent();
        
        // 2. Criamos o componente principal injetando a animação nele
        PlayerComponent playerComp = new PlayerComponent(animComp);

        // 3. Montamos a entidade com o tamanho ampliado em 4x
        // 1. Constrói a entidade normalmente
        Entity player = new EntityBuilder(data)
                .type(PLAYER)
                .with(animComp)
                .with(playerComp)
                .build();
        return player;
    }
}
*/