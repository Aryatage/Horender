package com.mycompany.horender;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void switchToGameplay() throws IOException {
        App.setRoot("gameplay");
    }
   @FXML
    private void switchToRanking() throws IOException {
        App.setRoot("ranking");
    }
    @FXML
    private void switchToCredits() throws IOException {
        App.setRoot("creditos");
    }
}