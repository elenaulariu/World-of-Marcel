package com.company;

public class Earth extends Spell {
    public Earth() {
        super(5, 8);
    }

    // Folosim sablonul visitor pentru a modela efectul pe care il are Spell asupra entitatilor
    public void visit(Entity entity) {
        if (entity.protectionEarth == false) {
            entity.currentLife = entity.currentLife - damage;
        }
    }

    public String toString() {
        return "Ice";
    }
}