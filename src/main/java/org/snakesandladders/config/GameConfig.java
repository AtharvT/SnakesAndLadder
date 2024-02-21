package org.snakesandladders.config;

import org.snakesandladders.model.Ladder;
import org.snakesandladders.model.Snake;

import java.util.List;

public class GameConfig {
    private int numberOfPlayers;
    private int boardSize;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private int numberOfDies;

    // Constructors
    public GameConfig() {
    }

    // Getters and setters
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public int getNumberOfDies() {
        return numberOfDies;
    }
}
