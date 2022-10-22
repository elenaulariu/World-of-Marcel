package com.company;

public class Cell {
    public int x, y;
    public CellEnum cellEnum;
    public CellElement content;
    public boolean visited;

    public Cell() {
        this.x = 0;
        this.y = 0;
        this.cellEnum = CellEnum.EMPTY;
        this.content = new Empty();
        this.visited = true;
    }

    public Cell(int x, int y, CellEnum cellEnum, CellElement content, boolean visited) {
        this.x = x;
        this.y = y;
        this.cellEnum = cellEnum;
        this.content = content;
        this.visited = visited;
    }
}