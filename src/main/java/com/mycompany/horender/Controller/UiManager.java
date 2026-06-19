/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender;

/**
 *
 * @author aluno
 */
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UiManager {

    private static HorenderApp app;
    private static Node inventoryNode;
    private static boolean inventoryVisible = false;
    private static boolean gameplayActive = false;

    // Sub‑cenas
    private static SubScene menuSubScene;
    private static SubScene rankingSubScene;
    private static SubScene creditsSubScene;
    private static SubScene upgradesSubScene;

    // Controladores
    private static MenuController menuController;
    private static InventoryController inventoryController;

    public static void init(HorenderApp appInstance) {
        app = appInstance;

        try {
            // Menu
            FXMLLoader menuLoader = loadFXML("/fxml/menu.fxml");
            Parent menuRoot = menuLoader.getRoot();
            menuController = menuLoader.getController();
            menuSubScene = createSubScene(menuRoot);
            menuSubScene.setSize(FXGL.getAppWidth(), FXGL.getAppHeight());

            // Ranking
            FXMLLoader rankingLoader = loadFXML("/fxml/ranking.fxml");
            Parent rankingRoot = rankingLoader.getRoot();
            rankingSubScene = createSubScene(rankingRoot);
            rankingSubScene.setSize(FXGL.getAppWidth(), FXGL.getAppHeight());

            // Créditos
            FXMLLoader creditsLoader = loadFXML("/fxml/credits.fxml");
            Parent creditsRoot = creditsLoader.getRoot();
            creditsSubScene = createSubScene(creditsRoot);
            creditsSubScene.setSize(FXGL.getAppWidth(), FXGL.getAppHeight());

            // Gameplay (sub‑cena vazia para representar o mundo do jogo)
            FXMLLoader gameplayLoader = loadFXML("/fxml/gameplay.fxml");
            Parent gameplayRoot = gameplayLoader.getRoot();
            // O gameplay não usa SubScene, mas sim a cena principal do jogo.
            // Apenas adicionamos o inventário ao overlay da cena principal.
            FXGL.getGameScene().addUINode(gameplayRoot);  // placeholder (vazio)

            // Inventário
            FXMLLoader inventoryLoader = loadFXML("/fxml/inventory.fxml");
            inventoryNode = inventoryLoader.getRoot();
            inventoryController = inventoryLoader.getController();
            inventoryNode.setVisible(false);
            FXGL.getGameScene().addUINode(inventoryNode);

            // Upgrades (preparada, mas não utilizada)
            FXMLLoader upgradesLoader = loadFXML("/fxml/upgrades.fxml");
            Parent upgradesRoot = upgradesLoader.getRoot();
            upgradesSubScene = createSubScene(upgradesRoot);
            upgradesSubScene.setSize(FXGL.getAppWidth(), FXGL.getAppHeight());

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar FXML", e);
        }
    }

    private static FXMLLoader loadFXML(String resourcePath) throws IOException {
        URL url = UIManager.class.getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(url);
        loader.load();
        return loader;
    }

    private static SubScene createSubScene(Parent root) {
        SubScene subScene = new SubScene();
        subScene.setContent(root);
        return subScene;
    }

    // ---------- Métodos de navegação ----------
    public static void showMenu() {
        FXGL.getSceneService().pushSubScene(menuSubScene);
        gameplayActive = false;
    }

    public static void showRanking() {
        FXGL.getSceneService().pushSubScene(rankingSubScene);
    }

    public static void showCredits() {
        FXGL.getSceneService().pushSubScene(creditsSubScene);
    }

    public static void startGameplay() {
        // Remove todas as sub‑cenas empilhadas (menu, ranking, etc.)
        while (!FXGL.getSceneService().getSubScenes().isEmpty()) {
            FXGL.getSceneService().popSubScene();
        }
        gameplayActive = true;
    }

    public static void goBack() {
        // Remove a sub‑cena atual (ranking, créditos) e volta à anterior (menu)
        if (!FXGL.getSceneService().getSubScenes().isEmpty()) {
            FXGL.getSceneService().popSubScene();
        }
    }

    public static void toggleInventory() {
        if (inventoryNode != null) {
            inventoryVisible = !inventoryVisible;
            inventoryNode.setVisible(inventoryVisible);
            inventoryController.onVisibilityChanged(inventoryVisible);
        }
    }

    public static boolean isGameplayActive() {
        return gameplayActive;
    }
}