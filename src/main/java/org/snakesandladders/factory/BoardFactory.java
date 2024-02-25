package org.snakesandladders.factory;

import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.InvalidGameElementException;
import org.snakesandladders.model.Board;
import org.snakesandladders.model.SnakesAndLadderBoard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory class for creating instances of different types of game boards.
 * This class utilizes the Factory design pattern to abstract the instantiation logic of game boards
 * based on the type of game specified.
 */
public class BoardFactory {

    private static final Logger logger = LoggerFactory.getLogger(BoardFactory.class);

    /**
     * Creates and returns a new board instance based on the specified game type.
     * Currently supports creating a board for the "SnakesAndLadders" game. And was not necessary but to make the code extensible added it.
     *
     * @param gameType The type of game for which the board is being created. For example, "SnakesAndLadders".
     * @param config   The configuration settings for the game board, which includes board size, number of snakes and ladders, etc.
     * @return A new instance of a board specific to the provided game type.
     * @throws InvalidGameElementException If an error occurs during the creation of game elements (e.g., snakes, ladders).
     * @throws IllegalArgumentException    If the provided game type is unknown or if the game configuration is null.
     */
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

