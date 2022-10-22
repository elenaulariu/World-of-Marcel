package com.company;

public class Fire extends Spell {
    public Fire() {
        super(15, 16);
    }

    // Folosim sablonul visitor pentru a modela efectul pe care il are Spell asupra entitatilor
    public void visit(Entity entity) {
        if (entity.protectionFire == false) {
            entity.currentLife = entity.currentLife - damage;
        }
    }

    public String toString() {
        return "Ice";
    }
}
