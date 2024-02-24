package org.snakesandladders.model;

import org.snakesandladders.exceptions.InvalidGameElementException;

public abstract class Board {
    protected int size;
    protected int endPosition;

    public Board(int size) {
        this.size = size;
        this.endPosition = size - 1;
    }

    protected abstract void initializeBoard() throws InvalidGameElementException;

    public abstract int getSize();
}
