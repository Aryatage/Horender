package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HealthDisplayComponent extends Component {
    private Text healthText;
    private HealthComponent health;

    @Override
    public void onAdded() {
        health = entity.getComponent(HealthComponent.class);
        healthText = new Text();
        healthText.setFill(Color.WHITE);
        healthText.setStroke(Color.BLACK);
        healthText.setStrokeWidth(0.5);
        healthText.setTranslateY(-20); // acima da entidade
        entity.getViewComponent().addChild(healthText);
    }

    @Override
    public void onUpdate(double tpf) {
        if (health != null) {
            healthText.setText(health.getCurrentHealth() + "/" + health.getMaxHealth());
        }
    }
}