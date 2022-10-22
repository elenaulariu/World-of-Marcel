package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Folosim sablonul Singleton cu instantiere intarziata
// Am creat o clasa suplimentara pentru folosirea sablonului
public class GameSingleton {
    private static GameSingleton instance;
    private List<Account> accounts;
    private Map<CellEnum, List<String>> dictionary;

    private GameSingleton() {
        this.accounts = new ArrayList<>();
        this.dictionary = new HashMap<>();
    }

    public static GameSingleton getInstance() {
        if (instance == null) {
            synchronized (Game.class) {
                if (instance == null) {
                    instance = new GameSingleton();
                }
            }
        }
        return instance;
    }

    // Constructor privat, trebuie sa construim metode get si set pentru elementele din clasa
    public static void setInstance(GameSingleton instance) {
        GameSingleton.instance = instance;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Map<CellEnum, List<String>> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<CellEnum, List<String>> dictionary) {
        this.dictionary = dictionary;
    }

    // Metode pentru a adauga elemente noi
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addToDictionary(CellEnum typeOfCell, List<String> story) {
        if (dictionary.containsKey(typeOfCell) == false) {
            dictionary.put(typeOfCell, story);
        }
    }

    public void addToDictionary(CellEnum typeOfCell, String story) {
        if (dictionary.containsKey(typeOfCell) == false) {
            List<String> text = new ArrayList<>();
            text.add(story);
            addToDictionary(typeOfCell, text);
        } else {
            dictionary.get(typeOfCell).add(story);
        }
    }
}