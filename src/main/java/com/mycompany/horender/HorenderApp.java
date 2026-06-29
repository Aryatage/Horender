package com.mycompany.horender;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.UserAction;
import com.mycompany.horender.Components.CombatComponent;
import com.mycompany.horender.Components.MonsterSpawnerComponent;
import com.mycompany.horender.Controllers.UIManager;
import com.mycompany.horender.Factories.HorenderFactory;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class HorenderApp extends GameApplication {
        public static boolean moveLeft = false;
        public static boolean moveRight = false;
        public static boolean attackRequested = false;   // NOVA
        
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(768);
        settings.setHeight(512);
        settings.setTitle("Horender");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
    FXGL.getGameWorld().addEntityFactory(new HorenderFactory());

    // Cenário
    Image scenarioImage = new Image(getClass().getResourceAsStream("/assets/textures/CENARIO.PNG"));
    Rectangle bgRect = new Rectangle(2048, 512);
    bgRect.setFill(new ImagePattern(scenarioImage));
    bgRect.setTranslateY(0); // ancora na base

    FXGL.entityBuilder()
        .view(bgRect)
        .zIndex(-100)
        .buildAndAttach();
    FXGL.entityBuilder()
    .with(new MonsterSpawnerComponent())
    .buildAndAttach();

    // Spawn
    Entity player = FXGL.spawn("player", UIManager.PLAYER_SPAWN_X, UIManager.PLAYER_SPAWN_Y);
    FXGL.spawn("monster01", UIManager.MONSTER_SPAWN_X, UIManager.MONSTER_SPAWN_Y);

    // Limites do mundo (sem bindToEntity, o componente controla a câmera)
    FXGL.getGameScene().getViewport().setBounds(0, 0, 2048, 512);
}

    @Override
    protected void initUI() {
  
        UIManager.init(this);
        UIManager.showMenu();
    }

    @Override
    protected void initInput() {
      Input input = FXGL.getInput();
      
      // Esquivas
    FXGL.onKeyDown(KeyCode.UP, () -> getPlayerCombat().ifPresent(c -> c.playerEvade(KeyCode.UP)));
    FXGL.onKeyDown(KeyCode.DOWN, () -> getPlayerCombat().ifPresent(c -> c.playerEvade(KeyCode.DOWN)));
    FXGL.onKeyDown(KeyCode.RIGHT, () -> getPlayerCombat().ifPresent(c -> c.playerEvade(KeyCode.RIGHT)));
    FXGL.onKeyDown(KeyCode.LEFT, () -> getPlayerCombat().ifPresent(c -> c.playerEvade(KeyCode.LEFT)));

        // Ataque do jogador (agora só na janela de contra‑ataque)
        FXGL.onKeyDown(KeyCode.J, () -> getPlayerCombat().ifPresent(CombatComponent::playerAttack));
        FXGL.onKeyDown(KeyCode.SPACE, () -> getPlayerCombat().ifPresent(CombatComponent::playerAttack));

    // Movimento – Esquerda
    input.addAction(new UserAction("Move Left A") {
        @Override protected void onActionBegin() { moveLeft = true; }
        @Override protected void onActionEnd() { moveLeft = false; }
    }, KeyCode.A);

    // Movimento – Direita
    input.addAction(new UserAction("Move Right D") {
        @Override protected void onActionBegin() { moveRight = true; }
        @Override protected void onActionEnd() { moveRight = false; }
    }, KeyCode.D);

    // Inventário (I)
    input.addAction(new UserAction("Toggle Inventory") {
        @Override protected void onActionBegin() {
            if (UIManager.isGameplayActive()) UIManager.toggleInventory();
        }
    }, KeyCode.I);
}
    private Optional<CombatComponent> getPlayerCombat() {
    return FXGL.getGameWorld().getEntitiesByType(EntitiesType.PLAYER)
            .stream().findFirst()
            .map(p -> p.getComponent(CombatComponent.class));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}