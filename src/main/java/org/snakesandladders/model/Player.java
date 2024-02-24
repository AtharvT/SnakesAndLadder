package org.snakesandladders.model;

public class Player {
    private final String name;
    private int position;

    public void setRemainingWaitingTurns(int remainingWaitingTurns) {
        this.remainingWaitingTurns = remainingWaitingTurns;
    }

    public int getRemainingWaitingTurns() {
        return remainingWaitingTurns;
    }

    private int remainingWaitingTurns;

    public boolean isWaiting() {
        return remainingWaitingTurns != 0;
    }

    private boolean isWaiting;

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    private boolean isWon;

    public Player(String name, int startingPosition) {
        this.name = name;
        this.position = startingPosition;
        this.remainingWaitingTurns = 0;
        this.isWaiting = false;
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

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }
}
