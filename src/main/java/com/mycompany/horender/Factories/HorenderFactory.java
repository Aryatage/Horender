/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Factories;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class HorenderFactory implements EntityFactory {

    private final PlayerFactory playerFactory = new PlayerFactory();
    private final FloorFactory floor = new FloorFactory();
    
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        // Delega a criação para a sub-fábrica especialista
        return playerFactory.createPlayer(data);
    }
    @Spawns("floor")
    public Entity newFloor (SpawnData data) {
        return floor.CreateFloor(data);
    }
}