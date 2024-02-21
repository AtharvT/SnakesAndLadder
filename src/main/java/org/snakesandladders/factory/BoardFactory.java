package org.snakesandladders.factory;

import org.snakesandladders.config.GameConfig;
import org.snakesandladders.model.Board;
import org.snakesandladders.model.SnakesAndLadderBoard;

public class BoardFactory {

    public static Board createBoard(String gameType, int size, GameConfig config, boolean loadFromConfig) {
        switch (gameType) {
            case "SnakesAndLadders":
                return new SnakesAndLadderBoard(size, config.getSnakes(), config.getLadders(), config.getSnakes().size(), config.getLadders().size(), loadFromConfig);
            default:
                throw new IllegalArgumentException("Unknown game type: " + gameType);
        }
    }
}

