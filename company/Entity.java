package com.company;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Element {
    public List<Spell> abilities;
    public int currentLife, maxLife;
    public int currentMana, maxMana;
    public boolean protectionFire, protectionIce, protectionEarth;

    public Entity() {
        this.abilities = new ArrayList<Spell>();
        this.currentLife = 100;
        this.maxLife = 100;
        this.currentMana = 100;
        this.maxMana = 100;
        this.protectionFire = false;
        this.protectionIce = false;
        this.protectionEarth = false;
    }

    public Entity(List<Spell> abilities, int currentLife, int currentMana, boolean protectionFire,
                  boolean protectionIce, boolean protectionEarth) {
        this.abilities = abilities;
        this.currentLife = currentLife;
        this.maxLife = 100;
        this.currentMana = currentMana;
        this.maxMana = 100;
        this.protectionFire = protectionFire;
        this.protectionIce = protectionIce;
        this.protectionEarth = protectionEarth;
    }

    // Regenereaza viata cu valoarea data, fara a trece de valoarea maxima
    public void regenerateLife(int value) {
        if ((currentLife + value) <= maxLife) {
            currentLife = currentLife + value;
        } else currentLife = maxLife;
    }

    // Regenereaza mana cu valoarea data, fara a trece de valoarea maxima
    public void regenerateMana(int value) {
        if ((currentMana + value) <= maxMana) {
            currentMana = currentMana + value;
        } else currentMana = maxMana;
    }

    public void addAbility(Spell ability) {
        abilities.add(ability);
    }

    // Foloseste abilitatea aleasa impotriva inamicului, atat timp cat ii
    // apartine abilitatea si are suficienta mana
    public boolean abilityUse(Spell ability, Entity currentEnemy) {
        if (currentMana > ability.manaCost && abilities.contains(ability)) {
            currentEnemy.accept(ability);
            currentMana = currentMana - ability.manaCost;
            return true;
        }
        return false;
    }

    public abstract void receiveDamage(int damage);

    public abstract int getDamage();

}