package com.mycompany.horender;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.mycompany.horender.Factories.HorenderFactory;
import static javafx.application.Application.launch;

/**
 * JavaFX App
 */


public class HorenderApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Horender");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        // Registra a Fábrica Principal no mundo do jogo
        FXGL.getGameWorld().addEntityFactory(new HorenderFactory());

        // Da o spawn do player na posição X: 400, Y: 300
        FXGL.getGameWorld().spawn("player", 400, 150);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 