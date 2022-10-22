package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement {
    public List<Potion> shop;

    // Adauga in shop o ManaPotion si un HealthPotion urmate de alte potiuni alese random
    public Shop() {
        shop = new ArrayList<Potion>();
        List<Potion> possiblePotions = new ArrayList<Potion>(2);
        possiblePotions.add(new ManaPotion());
        possiblePotions.add(new HealthPotion());
        Random random = new Random();
        shop.add(new ManaPotion());
        shop.add(new HealthPotion());
        for (int i = 0; i < random.nextInt(3); i++) {
            shop.add(possiblePotions.get(random.nextInt(possiblePotions.size())));
        }
    }

    public void deletePotion(int index) {
        shop.remove(index);
    }

    public String toString() {
        String shopElements = "";
        for (Potion obj : shop) {
            shopElements = shopElements + obj.potionUsage() + " ";
        }
        return shopElements;
    }

    public char toCharacter() {
        return 'S';
    }
}