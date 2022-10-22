package com.company;

import java.util.Random;

public class Enemy extends Entity implements CellElement {
    Random random = new Random();
    String[] ab = {"Ice", "Fire", "Earth"};

    // Initializeaza toate valorile cu valor random din intervale alese de noi
    public Enemy() {
        super();
        this.currentMana = random.nextInt(1, 101);
        this.currentLife = random.nextInt(1, 101);
        this.protectionIce = random.nextBoolean();
        this.protectionFire = random.nextBoolean();
        this.protectionEarth = random.nextBoolean();
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
    }

    // Exista o posibilitate de 50% ca inamicul sa nu primeasca damage
    public void receiveDamage(int damage) {
        boolean receive = random.nextBoolean();
        if (receive == true) {
            currentLife = currentLife - damage;
        }
    }

    // Exista o sansa de 50% ca inamicul sa ofere damage dublu
    public int getDamage() {
        int damage = 10;
        if (random.nextBoolean() == true) {
            return 2 * damage;
        } else return damage;
    }

    public char toCharacter() {
        return 'E';
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    // Functie care stabileste daca inamicul va ataca cu o abilitate(25% sanse, atat timp cat exista
    // abilitati) sau ataca simplu
    public void attack(Character character) {
        Random random = new Random();
        if (random.nextDouble() < 0.25 && !abilities.isEmpty()) {
            int index = random.nextInt(abilities.size());
            if (abilityUse(abilities.get(index), character) == true) {
                abilities.remove(index);
            }
        } else {
            character.receiveDamage(getDamage());
        }
    }
}