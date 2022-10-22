package com.company;

public class Ice extends Spell {
    public Ice() {
        super(10, 12);
    }

    // Folosim sablonul visitor pentru a modela efectul pe care il are Spell asupra entitatilor
    public void visit(Entity entity) {
        if (entity.protectionIce == false) {
            entity.currentLife = entity.currentLife - damage;
        }
    }

    public String toString() {
        return "Ice";
    }
}
