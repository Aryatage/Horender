package com.mycompany.horender.Controllers;

public class InventoryController {

    public void onVisibilityChanged(boolean visible) {
        System.out.println("Inventário " + (visible ? "aberto" : "fechado"));
    }
}