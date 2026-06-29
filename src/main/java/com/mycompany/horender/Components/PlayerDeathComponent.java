package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;
import com.mycompany.horender.Controllers.UIManager;

public class PlayerDeathComponent extends Component {

    private boolean deadTriggered = false;

    @Override
    public void onUpdate(double tpf) {
        if (deadTriggered) return;

        HealthComponent health = entity.getComponent(HealthComponent.class);
        if (health != null && health.isDead()) {
            deadTriggered = true;
            UIManager.showDeathscape();
        }
    }

    public void reset() {
        deadTriggered = false;
    }
}