package com.company;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public List<Potion> potions;
    public int maxWeight;
    public int numberOfCoins;

    // Ofera personajului 35 de banuti, suficienti pentru a cumpara o ManaPotion si un HealthPotion
    public Inventory() {
        this.potions = new ArrayList<Potion>();
        this.maxWeight = 35;
        this.numberOfCoins = 65;
    }

    public Inventory(List<Potion> potions, int numberOfCoins) {
        this.potions.addAll(potions);
        this.maxWeight = 35;
        this.numberOfCoins = numberOfCoins;
    }

    public int inventoryWeight() {
        int weight = 0;
        for (Object obj : potions) {
            weight = weight + ((Potion) obj).potionWeight();
        }
        return weight;
    }

    public void addPotion(Potion potion) {
        potions.add(potion);
        numberOfCoins = numberOfCoins - potion.potionPrice();
    }

    public void removePotion(Potion potion) {
        potions.remove(potion);
    }

    public int remainingWeight() {
        return maxWeight - inventoryWeight();
    }
}