package com.mycompany.horender;

public enum Upgrade {
    DAMAGE_BOOST("Ataque Brutal", 5, "Causa 35 de dano em vez de 20."),
    INVISIBILITY("Sombra Furtiva", 10, "Monstros não atacam você."),
    MONSTER_FEAST("Caos Bestial", 8, "Dobra o número de monstros e eles surgem mais rápido."),
    HEALTH_BOOST("Vigor Superior", 5, "Vida máxima aumentada para 150.");

    private final String name;
    private final int cost;
    private final String description;

    Upgrade(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public String getName() { return name; }
    public int getCost() { return cost; }
    public String getDescription() { return description; }
}