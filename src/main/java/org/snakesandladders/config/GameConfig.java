package org.snakesandladders.config;

import org.snakesandladders.model.gameelement.Crocodile;
import org.snakesandladders.model.gameelement.Ladder;
import org.snakesandladders.model.gameelement.Mine;
import org.snakesandladders.model.gameelement.Snake;
import org.snakesandladders.strategy.MovementStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.snakesandladders.utils.MovementStrategyDeserializer;

import java.util.ArrayList;
import java.util.List;

public class GameConfig implements Config {
    private int numberOfPlayers;
    private int boardSize;
    private int numberOfSnakes;
    private int numberOfLadders;
    private int numberOfDies;
    private List<Snake> snakeList = new ArrayList<>();
    private List<Ladder> ladderList = new ArrayList<>();
    private List<Crocodile> crocodiles = new ArrayList<>();
    private List<Mine> mines = new ArrayList<>();

    @JsonDeserialize(using = MovementStrategyDeserializer.class)
    private MovementStrategy movementStrategy;

    public GameConfig() {
        // No-arg constructor for JSON deserialization
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getBoardSize() {
        return boardSize*boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getNumberOfSnakes() {
        return numberOfSnakes;
    }

    public void setNumberOfSnakes(int numberOfSnakes) {
        this.numberOfSnakes = numberOfSnakes;
    }

    public int getNumberOfLadders() {
        return numberOfLadders;
    }

    public void setNumberOfLadders(int numberOfLadders) {
        this.numberOfLadders = numberOfLadders;
    }

    public int getNumberOfDies() {
        return numberOfDies;
    }

    public void setNumberOfDies(int numberOfDies) {
        this.numberOfDies = numberOfDies;
    }

    public List<Snake> getSnakeList() {
        return snakeList;
    }

    public void setSnakeList(List<Snake> snakeList) {
        this.snakeList = snakeList;
    }

    public List<Ladder> getLadderList() {
        return ladderList;
    }

    public void setLadderList(List<Ladder> ladderList) {
        this.ladderList = ladderList;
    }

    public List<Crocodile> getCrocodiles() {
        return crocodiles;
    }

    public void setCrocodiles(List<Crocodile> crocodiles) {
        this.crocodiles = crocodiles;
    }

    public List<Mine> getMines() {
        return mines;
    }

    public void setMines(List<Mine> mines) {
        this.mines = mines;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    @Override
    public void basicValidation() {
        List<String> errors = new ArrayList<>();

        if (numberOfDies <= 0) errors.add("Number of dice must be greater than 0.");
        if (numberOfLadders <= 0) errors.add("Number of ladders must be greater than 0.");
        if (numberOfPlayers <= 0) errors.add("Number of players must be greater than 0.");
        if (numberOfSnakes <= 0) errors.add("Number of snakes must be greater than 0.");
        if (boardSize <= 0) errors.add("Board size must be greater than 0.");

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Configuration validation failed: " + String.join(" ", errors));
        }
    }
}
