package com.mycompany.horender.Controller;

public class InventoryController {

    public void onVisibilityChanged(boolean visible) {
        System.out.println("Inventário " + (visible ? "aberto" : "fechado"));
    }
}