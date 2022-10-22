package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public static int length, width;
    public static Character character;
    public Cell currentCell;

    private Grid(int length, int width) {
        Grid.length = length;
        Grid.width = width;
    }

    // Initializam constructorul, celula curenta reprezentand prima celula a hartii
    private Grid(int length, int width, Character character) {
        Grid.length = length;
        Grid.width = width;
        Grid.character = character;
        this.currentCell = new Cell();
    }

    // Cream o harta random care contine cel putin 2 shop-uri si 4 inamici
    public static Grid mapGenerator(int length, int width) {
        Grid grid = new Grid(length, width);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            grid.add(new ArrayList<Cell>(width));
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                grid.get(i).add(new Cell(i, j, CellEnum.EMPTY, new Empty(), false));
            }
        }
        grid.get(length - 1).get(width - 1).cellEnum = CellEnum.FINISH;
        grid.get(length - 1).get(width - 1).content = new Finish();
        for (int i = 0; i < random.nextInt(2, 5); i++) {
            int x = random.nextInt(0, length);
            int y = random.nextInt(0, width);
            if ((x != 0 || y != 0) && (x != length || y != length)) {
                grid.get(x).get(y).cellEnum = CellEnum.SHOP;
                grid.get(x).get(y).content = new Shop();
            }
        }
        for (int i = 0; i < random.nextInt(4, 7); i++) {
            int x = random.nextInt(0, length);
            int y = random.nextInt(0, width);
            if ((x != 0 || y != 0) && grid.get(x).get(y).cellEnum == CellEnum.EMPTY) {
                grid.get(x).get(y).cellEnum = CellEnum.ENEMY;
                grid.get(x).get(y).content = new Enemy();
            }
        }
        return grid;
    }

    // Generam harta hardcodata din enunt
    public static Grid mapGeneratorHardcoded(int length, int width) {
        Grid grid = new Grid(length, width);
        for (int i = 0; i < length; i++) {
            grid.add(new ArrayList<Cell>(width));
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                grid.get(i).add(new Cell(i, j, CellEnum.EMPTY, new Empty(), false));
            }
        }
        grid.get(length - 1).get(width - 1).cellEnum = CellEnum.FINISH;
        grid.get(length - 1).get(width - 1).content = new Finish();
        grid.get(0).get(3).cellEnum = CellEnum.SHOP;
        grid.get(0).get(3).content = new Shop();
        grid.get(1).get(3).cellEnum = CellEnum.SHOP;
        grid.get(1).get(3).content = new Shop();
        grid.get(2).get(0).cellEnum = CellEnum.SHOP;
        grid.get(2).get(0).content = new Shop();
        grid.get(3).get(4).cellEnum = CellEnum.ENEMY;
        grid.get(3).get(4).content = new Enemy();
        return grid;
    }

    // Daca celula curenta nu se afla pe prima coloana, mergem in vest, si verificam daca exista banuti
    // in cazul unei celule goale, altfel afisam un mesaj
    // Respectam acelasi procedeu si pentru celelalte functii de deplasare
    public boolean goWest() {
        this.get(currentCell.x).get(currentCell.y).visited = true;
        if (currentCell.y > 0) {
            currentCell.y = currentCell.y - 1;
            character.y = currentCell.y;
            setCurrentCell(currentCell.x, currentCell.y);
            if (currentCell.visited == false && currentCell.cellEnum == CellEnum.EMPTY) {
                character.checkForCoins();
            }
            currentCell.visited = true;
            return true;
        } else System.out.println("The character can not move!");
        return false;
    }

    public boolean goEast() {
        this.get(currentCell.x).get(currentCell.y).visited = true;
        if (currentCell.y < width - 1) {
            currentCell.y = currentCell.y + 1;
            character.y = currentCell.y;
            setCurrentCell(currentCell.x, currentCell.y);
            if (currentCell.visited == false && currentCell.cellEnum == CellEnum.EMPTY) {
                character.checkForCoins();
            }
            currentCell.visited = true;
            return true;
        } else System.out.println("The character can not move!");
        return false;
    }

    public boolean goNorth() {
        this.get(currentCell.x).get(currentCell.y).visited = true;
        if (currentCell.x > 0) {
            currentCell.x = currentCell.x - 1;
            character.x = currentCell.x;
            setCurrentCell(currentCell.x, currentCell.y);
            if (currentCell.visited == false && currentCell.cellEnum == CellEnum.EMPTY) {
                character.checkForCoins();
            }
            currentCell.visited = true;
            return true;
        } else System.out.println("The character can not move!");
        return false;
    }

    public boolean goSouth() {
        this.get(currentCell.x).get(currentCell.y).visited = true;
        if (currentCell.x < length - 1) {
            currentCell.x = currentCell.x + 1;
            character.x = currentCell.x;
            setCurrentCell(currentCell.x, currentCell.y);
            if (currentCell.visited == false && currentCell.cellEnum == CellEnum.EMPTY) {
                character.checkForCoins();
            }
            currentCell.visited = true;
            return true;
        } else System.out.println("The character can not move!");
        return false;
    }

    // Afisam harta (? daca celula nu a fost vizitata, si unul dintre simbolurile alese conform tipului
    // celulei in caz contrar, pe celula pe care se afla personajul se va afisa P)
    public String printMap() {
        String map = "";
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (this.currentCell.x == i && this.currentCell.y == j) {
                    if (this.currentCell.cellEnum == CellEnum.EMPTY) {
                        map = map + "P\t";
                    } else {
                        map = map + "P" + currentCell.content.toCharacter() + "\t";
                    }
                } else {
                    if (((Cell) ((ArrayList) get(i)).get(j)).visited) {
                        map = map + ((Cell) ((ArrayList) get(i)).get(j)).content.toCharacter() + "\t";
                    } else map = map + "?\t";
                }
            }
            map = map + "\n";
        }
        return map;
    }

    public void setCharacter(Character character) {
        Grid.character = character;
    }

    public void setCurrentCell() {
        this.currentCell = new Cell();
    }

    public void setCurrentCell(int x, int y) {
        this.currentCell = new Cell(x, y, this.get(x).get(y).cellEnum, this.get(x).get(y).content, this.get(x).get(y).visited);
    }
}