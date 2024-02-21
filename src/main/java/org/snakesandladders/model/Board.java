package org.snakesandladders.model;

public abstract class Board {
    protected int size;
    protected int startPosition = 1;
    protected int endPosition;

    public Board(int size) {
        this.size = size;
        this.endPosition = size-1;
        initializeBoard();
    }

    protected abstract void initializeBoard();

}
