package com.company;

import java.util.Random;

public class Warrior extends Character {
    public Warrior(String name, int experience, int level) {
        super(name, experience, level);
        this.inventory.maxWeight = 40;
        this.protectionFire = true;
        this.strength = this.level * 10;
        this.charisma = this.level * 5;
        this.dexterity = this.level * 5;
    }

    // Creste nivelul daca experienta e cel putin egala cu de 10 ori nivelul
    // Creste valoarea maxima a manei si a vietii, si regenereaza valoriile curente
    // Creste atributele si adauga intre 2 si 4 noi abilitati
    public void raiseLevel() {
        if (experience >= level * 10) {
            experience = experience - level * 10;
            level = level + 1;
            maxMana = maxMana + 10;
            maxLife = maxLife + 20;
            currentMana = maxMana;
            currentLife = maxLife;
            charisma = charisma + 5;
            strength = strength + 10;
            dexterity = dexterity + 5;
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
    }

    public String toString() {
        return "Warrior\tName:" + name + "\tExperience:" + experience + "\tLevel:" + level;
    }

    // Personajului ii scade valoarea curenta a vietii cu valoarea data de oponent, sau cu jumatate
    // in functie de atributele secundare
    public void receiveDamage(int damage) {
        float chance = (charisma + dexterity) / 10.f;
        Random random = new Random();
        if (random.nextFloat() < chance) {
            damage = damage / 2;
        }
        currentLife = currentLife - damage;
    }

    // Personajul ofera oponentului un damage egal cu atributul principal, sau dublu
    public int getDamage() {
        int damage = strength;
        float chance = strength / 10.f;
        Random random = new Random();
        if (random.nextFloat() < chance) {
            return damage * 2;
        } else return damage;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
