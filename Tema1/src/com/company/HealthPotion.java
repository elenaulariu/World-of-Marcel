package com.company;

public class HealthPotion implements Potion {
    private final int price;
    private final int regeneration;
    private final int weight;

    public HealthPotion() {
        this.price = 20;
        this.regeneration = 25;
        this.weight = 5;
    }

    public String potionUsage() {
        return "Health Potion";
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