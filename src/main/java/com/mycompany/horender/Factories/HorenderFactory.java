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
    private final MonsterFactory monsterFactory = new MonsterFactory();

    @Spawns("player")
    public Entity newPlayer(SpawnData data) { return playerFactory.createPlayer(data); }
    @Spawns("monster01")
    public Entity newMonster(SpawnData data) { return monsterFactory.createMonster(data); }
    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
    return new CoinFactory().createCoin(data);
}
}