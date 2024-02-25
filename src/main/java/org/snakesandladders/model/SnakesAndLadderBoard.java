package org.snakesandladders.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.InvalidGameElementException;
import org.snakesandladders.model.gameelement.GameElement;
import org.snakesandladders.model.gameelement.JumperEffect;

import java.util.*;

public class SnakesAndLadderBoard extends Board {
    private static final Logger logger = LoggerFactory.getLogger(SnakesAndLadderBoard.class);

    private final List<GameElement> gameElements = new ArrayList<>();
    private final Map<Integer, GameElement> positionToElement = new HashMap<>();
    private final GameConfig config;

    public SnakesAndLadderBoard(GameConfig config) throws InvalidGameElementException {
        super(config.getBoardSize());
        logger.info("Initializing Snakes and Ladder Board with size {}", config.getBoardSize());
        this.config = config;
        initializeBoard();
    }

    @Override
    protected void initializeBoard() throws InvalidGameElementException {
        logger.info("Adding Game Elements to the board");
        gameElements.addAll(config.getSnakeList());
        gameElements.addAll(config.getLadderList());
        for (GameElement element : gameElements) {
            if (!isPlacementValid(element)) {
                logger.error("Invalid placement for game element at position: {}", element.getStart());
                throw new InvalidGameElementException("Invalid placement for game element at position: " + element.getStart());
            }
            positionToElement.put(element.getStart(), element);
            positionToElement.put(element.getEnd(), element);
        }
    }

    private boolean isPlacementValid(GameElement element) {
        return !positionToElement.containsKey(element.getStart()) || !positionToElement.containsKey(element.getEnd());
    }


    @Override
    public int getSize() {
        return size;
    }

    public int getNewPosition(int currentPosition, Player player) {
        GameElement element = positionToElement.get(currentPosition);
        if (element != null) {
            JumperEffect effect = element.applyEffect(player);
            logger.info("Player {} encountered a game element at position {}. Applying effect.", player.getName(), currentPosition);
            if (effect.newPosition() != null) {
                logger.info("Player {} moves to new position {}", player.getName(), effect.newPosition());
                return effect.newPosition();
            }

            if (effect.turnsHeld() != null) {
                logger.info("Player {} is held for {} turns", player.getName(), effect.turnsHeld());
                player.setWaitingTurns(effect.turnsHeld());
            }
        }
        return currentPosition;
    }

    public List<GameElement> getGameElements() {
        return gameElements;
    }
}
