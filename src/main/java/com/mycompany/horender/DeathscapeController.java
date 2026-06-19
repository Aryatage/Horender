package com.mycompany.horender;

import java.io.IOException;
import javafx.fxml.FXML;

public class DeathscapeController {

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
}
