package com.mycompany.horender.Controllers;

import com.almasb.fxgl.dsl.FXGL;
import com.mycompany.horender.HorenderApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public abstract class UIManager {

    private static HorenderApp app;
    private static Node inventoryNode;
    private static boolean inventoryVisible = false;
    private static boolean gameplayActive = false;

    // No FXGL 21+, usamos Pane ou StackPane como containers
    private static Pane menuPane;
    private static Pane rankingPane;
    private static Pane creditsPane;
    private static Pane upgradesPane;

    private static InventoryController inventoryController;

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
}
    
private static FXMLLoader loadFXML(String resourcePath) throws IOException {
    System.out.println("==========================================");
    System.out.println("Tentando carregar: " + resourcePath);
    
    URL url = UIManager.class.getResource(resourcePath);
    System.out.println("URL: " + url);
    
    if (url == null) {
        System.err.println("ERRO: URL é null para: " + resourcePath);
        throw new IOException("Recurso não encontrado: " + resourcePath);
    }
    
    FXMLLoader loader = new FXMLLoader(url);
    
    try {
        loader.load();
        System.out.println("SUCESSO: " + resourcePath + " carregado!");
    } catch (IOException e) {
        System.err.println("FALHA ao carregar: " + resourcePath);
        System.err.println("Causa: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
    
    return loader;
}
   /*private static FXMLLoader loadFXML(String resourcePath) throws IOException {
        URL url = UIManager.class.getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(url);
        loader.load();
        return loader;
    }
*/
    // Navegação - FXGL 21+ usa método diferente
    public static void showMenu() {
        FXGL.getGameScene().addUINode(menuPane);
        hideAllExcept(menuPane);
        gameplayActive = false;
    }

    public static void showRanking() {
        FXGL.getGameScene().addUINode(rankingPane);
        hideAllExcept(rankingPane);
    }

    public static void showCredits() {
        FXGL.getGameScene().addUINode(creditsPane);
        hideAllExcept(creditsPane);
    }

    public static void startGameplay() {
        hideAllExcept(null); // Esconde todos os menus
        gameplayActive = true;
    }

    public static void goBack() {
        // Volta para o menu
        FXGL.getGameScene().removeUINode(rankingPane);
        FXGL.getGameScene().removeUINode(creditsPane);
        showMenu();
    }

    private static void hideAllExcept(Pane visiblePane) {
        // Remove todos os panes de menu
        FXGL.getGameScene().removeUINode(menuPane);
        FXGL.getGameScene().removeUINode(rankingPane);
        FXGL.getGameScene().removeUINode(creditsPane);
        FXGL.getGameScene().removeUINode(upgradesPane);
        
        // Adiciona o painel visível
        if (visiblePane != null) {
            FXGL.getGameScene().addUINode(visiblePane);
        }
    }

    public static void toggleInventory() {
        if (inventoryNode != null) {
            inventoryVisible = !inventoryVisible;
            inventoryNode.setVisible(inventoryVisible);
            if (inventoryController != null) {
                inventoryController.onVisibilityChanged(inventoryVisible);
            }
        }
    }

    public static boolean isGameplayActive() {
        return gameplayActive;
    }
}