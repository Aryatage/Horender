package com.mycompany.horender;

import java.io.IOException;
import javafx.fxml.FXML;

public class GameplayController {

    @FXML
    private void switchToUpgrades() throws IOException {
        App.setRoot("deathscape");
    }
}
