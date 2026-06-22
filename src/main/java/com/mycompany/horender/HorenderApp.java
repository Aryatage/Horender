package com.mycompany.horender;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.mycompany.horender.Controller.UIManager;
import com.mycompany.horender.Factories.HorenderFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class HorenderApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Horender");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        // Registra a fábrica que sabe criar "player" e "floor"
   // FXGL.getGameWorld().addEntityFactory(new HorenderFactory());
       FXGL.getGameWorld().addEntityFactory(new HorenderFactory());
    // Spawna o player na posição (300, 300) – ajuste conforme necessário
   // FXGL.spawn("player", 300, 300);
      FXGL.spawn("player", 300, 200);
}

    @Override
    protected void initUI() {
        UIManager.init(this);
        UIManager.showMenu();
    }

    @Override
    protected void initInput() {
        FXGL.onKeyDown(KeyCode.I, () -> {
            if (UIManager.isGameplayActive()) {
                UIManager.toggleInventory();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}