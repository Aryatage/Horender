package com.mycompany.horender.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.mycompany.horender.Controllers.UIManager;
import com.mycompany.horender.EntitiesType;

public class MonsterSpawnerComponent extends Component {
    private double timer = 0;

    @Override
    public void onUpdate(double tpf) {
        if (!UIManager.isGameplayActive() || CombatComponent.combatActive) return;
        timer += tpf;
        double interval = UIManager.hasMonsterFeast() ? 3.0 : 5.0;
        int maxMonsters = UIManager.hasMonsterFeast() ? 4 : 2;
        if (timer >= interval) {
            timer = 0;
            long count = FXGL.getGameWorld().getEntitiesByType(EntitiesType.MONSTER01).size();
            if (count < maxMonsters) {
                double x = Math.random() > 0.5 ? 200 : 1800;
                FXGL.spawn("monster01", x, UIManager.MONSTER_SPAWN_Y);
            }
        }
    }
}