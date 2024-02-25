package org.snakesandladders.model;

public class Player {
    private final String name;
    private int position;
    private int remainingWaitingTurns;
    private boolean isWon;

    public Player(String name, int startingPosition) {
        this.name = name;
        this.position = startingPosition;
        this.remainingWaitingTurns = 0;
        this.isWon = false;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isWaiting() {
        return remainingWaitingTurns > 0;
    }

    public void setWaitingTurns(int turns) {
        this.remainingWaitingTurns = turns;
    }

    public void decrementWaitingTurn() {
        if (this.remainingWaitingTurns > 0) {
            this.remainingWaitingTurns--;
        }
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }
}
