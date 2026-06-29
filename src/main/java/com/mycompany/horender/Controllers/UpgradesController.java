package com.mycompany.horender.Controllers;

import com.mycompany.horender.Upgrade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UpgradesController {

    @FXML private Label coinLabel;
    @FXML private Label damageStatus;
    @FXML private Label invisibilityStatus;
    @FXML private Label monsterStatus;
    @FXML private Label healthStatus;

    @FXML private void initialize() {
        updateUI();
    }

    private void updateUI() {
        coinLabel.setText("Moedas: " + UIManager.getCoinCount()); // precisamos expor getCoinCount()
        damageStatus.setText(UIManager.hasDamageBoost() ? "Comprado" : "");
        invisibilityStatus.setText(UIManager.hasInvisibility() ? "Comprado" : "");
        monsterStatus.setText(UIManager.hasMonsterFeast() ? "Comprado" : "");
        healthStatus.setText(UIManager.hasHealthBoost() ? "Comprado" : "");
    }

    @FXML private void onBuyDamage() { buyUpgrade(Upgrade.DAMAGE_BOOST); }
    @FXML private void onBuyInvisibility() { buyUpgrade(Upgrade.INVISIBILITY); }
    @FXML private void onBuyMonster() { buyUpgrade(Upgrade.MONSTER_FEAST); }
    @FXML private void onBuyHealth() { buyUpgrade(Upgrade.HEALTH_BOOST); }

    private void buyUpgrade(Upgrade upgrade) {
        if (UIManager.purchaseUpgrade(upgrade)) {
            updateUI();
        }
    }

    @FXML private void onRestart() {
        UIManager.restartGame();
    }
}