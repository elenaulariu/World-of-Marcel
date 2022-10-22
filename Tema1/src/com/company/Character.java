package com.company;

import java.util.Random;

public abstract class Character extends Entity {
    public String name;
    public int x, y;
    public Inventory inventory;
    public int experience;
    public int level;
    public int strength, charisma, dexterity;
    Random random = new Random();
    String[] ab = {"Ice", "Fire", "Earth"};

    public Character(String name, int experience, int level) {
        super();
        for (int i = 0; i < random.nextInt(2, 5); i++) {
            String ability = ab[random.nextInt(0, 3)];
            if (ability.equals("Ice")) {
                abilities.add(new Ice());
            }
            if (ability.equals("Fire")) {
                abilities.add(new Fire());
            }
            if (ability.equals("Earth")) {
                abilities.add(new Earth());
            }
        }
        this.inventory = new Inventory();
        this.name = name;
        this.x = 0;
        this.y = 0;
        this.experience = experience;
        this.level = level;
        this.strength = 0;
        this.charisma = 0;
        this.dexterity = 0;
        this.maxMana = this.maxMana + (level - 1) * 10;
        this.currentMana = this.maxMana;
        this.maxLife = this.maxLife + (level - 1) * 20;
        this.currentLife = this.maxLife;
    }

    // Exista posibilitatea de 20% sa gaseasca o suma random de banuti pe casuta curenta
    public void checkForCoins() {
        Random random = new Random();
        if (random.nextInt(100) % 5 == 0) {
            int coins = random.nextInt(25);
            System.out.println("Found " + coins + " coins");
            inventory.numberOfCoins = inventory.numberOfCoins + coins;
        }
    }

    // Creste nivelul personajului
    public abstract void raiseLevel();

    // Cumpara o potiune daca are suficienti bani si spatiu
    public void buyPotion(Potion potion) {
        if (inventory.numberOfCoins > potion.potionPrice() && inventory.maxWeight > (inventory.inventoryWeight() + potion.potionWeight())) {
            inventory.addPotion(potion);
        }
    }
}