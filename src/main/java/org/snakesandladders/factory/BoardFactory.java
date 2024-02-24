package org.snakesandladders.factory;

import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.InvalidGameElementException;
import org.snakesandladders.model.Board;
import org.snakesandladders.model.SnakesAndLadderBoard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardFactory {

    private static final Logger logger = LoggerFactory.getLogger(BoardFactory.class);

    public static Board createBoard(String gameType, GameConfig config) throws InvalidGameElementException {
        if (config == null) {
            throw new IllegalArgumentException("GameConfig must not be null");
        }

        logger.info("Creating board for game type: {}", gameType);

        return switch (gameType) {
            case "SnakesAndLadders" -> new SnakesAndLadderBoard(config);
            default -> {
                logger.error("Unknown game type requested: {}", gameType);
                throw new IllegalArgumentException("Unknown game type: " + gameType);
            }
        };
    }
}

