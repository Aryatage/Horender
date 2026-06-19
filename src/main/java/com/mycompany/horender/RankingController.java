package com.mycompany.horender;

import java.io.IOException;
import javafx.fxml.FXML;

public class RankingController {

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
}
