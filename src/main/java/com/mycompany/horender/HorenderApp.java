package com.mycompany.horender;
/**
 * JavaFX HorenderApp
 */

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class HorenderApp extends GameApplication{
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Horender");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        // Carrega a fonte Impact para uso em toda a aplicação
        Font.loadFont(getClass().getResourceAsStream("/fonts/Impact.ttf"), 72);
    }

    @Override
    protected void initUI() {
        UIManager.init(this);
        UIManager.showMenu();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.I, () -> {
            // Ativa/desativa inventário apenas durante o gameplay
            if (UIManager.isGameplayActive()) {
                UIManager.toggleInventory();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}