package com.mycompany.horender.Controllers;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.mycompany.horender.Components.CoinComponent;
import com.mycompany.horender.Components.PlayerDeathComponent;
import com.mycompany.horender.EntitiesType;
import com.mycompany.horender.HorenderApp;
import com.mycompany.horender.Upgrade;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;

public abstract class UIManager {
        public static int getCoinCount() { return coinCount; }
        public static final double PLAYER_SPAWN_X = 300;
        public static final double PLAYER_SPAWN_Y = 235;

        public static final double MONSTER_SPAWN_X = 800;
        public static final double MONSTER_SPAWN_Y = 330;
        
    private static HorenderApp app;
    private static Node inventoryNode;
    private static boolean inventoryVisible = false;
    private static boolean gameplayActive = false;
    
    // Containers das telas
    private static Pane menuPane;
    private static Pane rankingPane;
    private static Pane creditsPane;
    private static Pane upgradesPane;

    // HUD
    private static Label playerHealthLabel;
    private static Label combatMessage;
    private static int coinCount = 0;
    private static Label coinLabel;
    private static InventoryController inventoryController;
    // Upgrades – flags permanentes                     
    
    private static boolean damageBoost = false;
    private static boolean invisibility = false;
    private static boolean monsterFeast = false;
    private static boolean healthBoost = false;

    public static boolean hasDamageBoost() { return damageBoost; }
    public static boolean hasInvisibility() { return invisibility; }
    public static boolean hasMonsterFeast() { return monsterFeast; }
    public static boolean hasHealthBoost() { return healthBoost; }

    public static boolean purchaseUpgrade(Upgrade upgrade) {
    if (coinCount < upgrade.getCost()) return false;
    coinCount -= upgrade.getCost();
    if (coinLabel != null) coinLabel.setText("Moedas: " + coinCount);

    switch (upgrade) {
        case DAMAGE_BOOST -> damageBoost = true;
        case INVISIBILITY -> invisibility = true;
        case MONSTER_FEAST -> monsterFeast = true;
        case HEALTH_BOOST -> healthBoost = true;
    }
    return true;
}
    // =============================================
    // Inicialização
    // =============================================
    public static void init(HorenderApp appInstance) {
        app = appInstance;

        try {
            System.out.println("\n=== INICIANDO CARREGAMENTO DOS FXMLs ===");

            // Menu
            System.out.println("\n[1/6] Carregando menu...");
            FXMLLoader menuLoader = loadFXML("/fxml/menu.fxml");
            menuPane = (Pane) menuLoader.getRoot();
            menuPane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
            System.out.println("[1/6] Menu OK!");

            // Ranking
            System.out.println("\n[2/6] Carregando ranking...");
            FXMLLoader rankingLoader = loadFXML("/fxml/ranking.fxml");
            rankingPane = (Pane) rankingLoader.getRoot();
            rankingPane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
            System.out.println("[2/6] Ranking OK!");

            // Créditos
            System.out.println("\n[3/6] Carregando créditos...");
            FXMLLoader creditsLoader = loadFXML("/fxml/creditos.fxml");
            creditsPane = (Pane) creditsLoader.getRoot();
            creditsPane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
            System.out.println("[3/6] Créditos OK!");

            // Gameplay
            System.out.println("\n[4/6] Carregando gameplay...");
            FXMLLoader gameplayLoader = loadFXML("/fxml/gameplay.fxml");
            Parent gameplayRoot = gameplayLoader.getRoot();
            FXGL.getGameScene().addUINode(gameplayRoot);
            System.out.println("[4/6] Gameplay OK!");

            // Inventário
            System.out.println("\n[5/6] Carregando inventário...");
            FXMLLoader inventoryLoader = loadFXML("/fxml/inventory.fxml");
            inventoryNode = inventoryLoader.getRoot();
            inventoryController = inventoryLoader.getController();
            inventoryNode.setVisible(false);
            FXGL.getGameScene().addUINode(inventoryNode);
            System.out.println("[5/6] Inventário OK!");

            // Upgrades
            System.out.println("\n[6/6] Carregando upgrades...");
            FXMLLoader upgradesLoader = loadFXML("/fxml/upgrades.fxml");
            upgradesPane = (Pane) upgradesLoader.getRoot();
            upgradesPane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
            System.out.println("[6/6] Upgrades OK!");

            System.out.println("\n=== TODOS OS FXMLs CARREGADOS COM SUCESSO! ===\n");

        } catch (IOException e) {
            System.err.println("\n!!! ERRO NO CARREGAMENTO !!!");
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar FXML", e);
        }

        // Label de combate (inicialmente invisível)
        combatMessage = new Label();
        combatMessage.setStyle("-fx-font-size: 32; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10;");
        combatMessage.setVisible(false);
        FXGL.getGameScene().addUINode(combatMessage);
        
        // Label de vida do jogador
        playerHealthLabel = new Label("100/100");
        playerHealthLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.5); -fx-padding: 5;");
        playerHealthLabel.setTranslateX(20);
        playerHealthLabel.setTranslateY(20);

        // Label de moedas
        coinLabel = new Label("Moedas: 0");
        coinLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.5); -fx-padding: 5;");
        coinLabel.setTranslateX(FXGL.getAppWidth() - 150);
        coinLabel.setTranslateY(20);
   
    }
    private static void addGameHUD() {
    if (playerHealthLabel.getParent() == null) {
        FXGL.getGameScene().addUINode(playerHealthLabel);
    }
    if (coinLabel != null && coinLabel.getParent() == null) {
        FXGL.getGameScene().addUINode(coinLabel);
    }
    }

    private static void removeGameHUD() {
    FXGL.getGameScene().removeUINode(playerHealthLabel);
    if (coinLabel != null) {
        FXGL.getGameScene().removeUINode(coinLabel);
    }
}
    public static void showDeathscape() {
    gameplayActive = false;
    removeGameHUD();                 // remove os labels de vida/moedas
    hideAllExcept(null);             // remove menus antigos
    FXGL.getGameScene().addUINode(upgradesPane);
}

public static void restartGame() {
    // Remove entidades antigas
    FXGL.getGameWorld().getEntitiesByType(EntitiesType.PLAYER).forEach(Entity::removeFromWorld);
    FXGL.getGameWorld().getEntitiesByType(EntitiesType.MONSTER01).forEach(Entity::removeFromWorld);
    FXGL.getGameWorld().getEntitiesFiltered(e -> e.hasComponent(CoinComponent.class))
       .forEach(Entity::removeFromWorld);

    // Reseta moedas
    coinCount = 0;
    if (coinLabel != null) coinLabel.setText("Moedas: 0");

    // Spawn inicial
    Entity newPlayer = FXGL.spawn("player", PLAYER_SPAWN_X, PLAYER_SPAWN_Y);
    FXGL.spawn("monster01", MONSTER_SPAWN_X, MONSTER_SPAWN_Y);

    // Câmera
    var viewport = FXGL.getGameScene().getViewport();
    viewport.bindToEntity(newPlayer, 0, 0);
    viewport.setBounds(0, 0, 2048, 512);
    viewport.setY(0);

    // Reseta componente de morte
    newPlayer.getComponentOptional(PlayerDeathComponent.class)
        .ifPresent(PlayerDeathComponent::reset);

    // Sai da Deathscape e inicia gameplay
    FXGL.getGameScene().removeUINode(upgradesPane);
    startGameplay();
}
    
    
    
    // =============================================
    // Mensagens de combate
    // =============================================
    public static void showCombatMessage(String text) {
        if (combatMessage != null) {
            combatMessage.setText(text);
            combatMessage.setVisible(true);
            combatMessage.setTranslateX(256 - combatMessage.getWidth() / 4);
            combatMessage.setTranslateY(160);
        }
    }

    public static void hideCombatMessage() {
        if (combatMessage != null) {
            combatMessage.setVisible(false);
        }
    }

    // =============================================
    // HUD do jogador
    // =============================================
    public static void updatePlayerHealthText(int current, int max) {
        if (playerHealthLabel != null) {
            playerHealthLabel.setText(current + "/" + max);
        }
    }



    // =============================================
    // Carregamento de FXML
    // =============================================
    private static FXMLLoader loadFXML(String resourcePath) throws IOException {
        System.out.println("==========================================");
        System.out.println("Tentando carregar: " + resourcePath);

        URL url = UIManager.class.getResource(resourcePath);
        System.out.println("URL: " + url);

        if (url == null) {
            throw new IOException("Recurso não encontrado: " + resourcePath);
        }

        FXMLLoader loader = new FXMLLoader(url);
        try {
            loader.load();
            System.out.println("SUCESSO: " + resourcePath + " carregado!");
        } catch (IOException e) {
            System.err.println("FALHA ao carregar: " + resourcePath);
            throw e;
        }
        return loader;
    }

    // =============================================
    // Navegação entre telas
    // =============================================
        public static void showMenu() {
    FXGL.getGameScene().addUINode(menuPane);
    removeGameHUD();
    hideAllExcept(menuPane);
    gameplayActive = false;
}

    public static void showRanking() {
    FXGL.getGameScene().addUINode(rankingPane);
    removeGameHUD();
    hideAllExcept(rankingPane);
    }

    public static void showCredits() {
    FXGL.getGameScene().addUINode(creditsPane);
    removeGameHUD();
    hideAllExcept(creditsPane);
    }

    public static void goBack() {
    FXGL.getGameScene().removeUINode(rankingPane);
    FXGL.getGameScene().removeUINode(creditsPane);
    removeGameHUD();
    showMenu();
    }

    public static void startGameplay() {
    removeGameHUD();                 // previne duplicatas
    hideAllExcept(null);
    addGameHUD();                    // recoloca os labels
    gameplayActive = true;
}
    public static void addCoins(int amount) {
    coinCount += amount;
    if (coinLabel != null) coinLabel.setText("Moedas: " + coinCount);
    }
   
    private static void hideAllExcept(Pane visiblePane) {
        FXGL.getGameScene().removeUINode(menuPane);
        FXGL.getGameScene().removeUINode(rankingPane);
        FXGL.getGameScene().removeUINode(creditsPane);
        FXGL.getGameScene().removeUINode(upgradesPane);

        if (visiblePane != null) {
            FXGL.getGameScene().addUINode(visiblePane);
        }
    }

    // =============================================
    // Inventário
    // =============================================
    public static void toggleInventory() {
        if (inventoryNode != null) {
            inventoryVisible = !inventoryVisible;
            inventoryNode.setVisible(inventoryVisible);
            if (inventoryController != null) {
                inventoryController.onVisibilityChanged(inventoryVisible);
            }
        }
    }

    // =============================================
    // Getters / flags
    // =============================================
    public static boolean isGameplayActive() {
        return gameplayActive;
    }
}