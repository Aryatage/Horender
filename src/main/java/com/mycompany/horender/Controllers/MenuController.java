package com.mycompany.horender.Controllers;

import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void onJogar() {
        UIManager.startGameplay();
    }

    @FXML
    private void onRanking() {
        UIManager.showRanking();
    }

    @FXML
    private void onCreditos() {
        UIManager.showCredits();
    }
}