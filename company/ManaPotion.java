package com.company;

public class ManaPotion implements Potion {
    private final int price;
    private final int regeneration;
    private final int weight;

    public ManaPotion() {
        this.price = 45;
        this.regeneration = 40;
        this.weight = 10;
    }

    public String potionUsage() {
        return "Mana Potion";
    }

    public int potionPrice() {
        return price;
    }

    public int regenerationValue() {
        return regeneration;
    }

    public int potionWeight() {
        return weight;
    }
}