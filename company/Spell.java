package com.company;

public abstract class Spell implements Visitor {
    public int damage, manaCost;

    public Spell(int damage, int manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;
    }
}