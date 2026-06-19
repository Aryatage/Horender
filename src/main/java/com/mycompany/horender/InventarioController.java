package com.mycompany.horender;

import java.io.IOException;
import javafx.fxml.FXML;

public class InventarioController {

    @FXML
    private void switchToGameplay() throws IOException {
        App.setRoot("Gameplay");
    }
}
