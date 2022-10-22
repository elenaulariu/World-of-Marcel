package com.company;

// Folosim sablonul factory pentru a crea personajele din fiecare cont
public class CharacterFactory {
    public static Character createCharacter(CharacterType characterType, String name, int experience, int level) {
        switch (characterType) {
            case Mage:
                return new Mage(name, experience, level);
            case Rogue:
                return new Rogue(name, experience, level);
            case Warrior:
                return new Warrior(name, experience, level);
        }
        throw new IllegalArgumentException("The character type " +
                characterType + " is not recognized.");
    }

    public enum CharacterType {
        Mage,
        Rogue,
        Warrior
    }
}