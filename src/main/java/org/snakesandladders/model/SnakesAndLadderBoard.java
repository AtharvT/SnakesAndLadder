package org.snakesandladders.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SnakesAndLadderBoard extends Board {
    private final int startPosition = 1;

    private final List<Snake> snakes;
    private final List<Ladder> ladders;
    private final Map<Integer, Integer> snakeHeadPositions = new HashMap<>();
    private Map<Integer, Integer> ladderBottomPositions = new HashMap<>();
    private Map<Integer, Integer> crocodile = new HashMap<>();
    private Map<Integer, Integer> mine = new HashMap<>();
    private final Set<String> snakeAndLadderSet = new HashSet<>();

    private final boolean loadFromConfig;
    private final int numberOfSnakes;
    private final int numberOfLadders;

    public SnakesAndLadderBoard(int size, List<Snake> snakes, List<Ladder> ladders, int numberOfSnakes, int numberOfLadders, boolean loadFromConfig) {
        super(size);
        this.snakes = snakes;
        this.ladders = ladders;
        this.numberOfSnakes = numberOfSnakes;
        this.numberOfLadders = numberOfLadders;
        this.loadFromConfig = loadFromConfig;
    }

    @Override
    protected void initializeBoard() {
        if (!loadFromConfig) {
            generateRandomPositions(numberOfSnakes, true);
            generateRandomPositions(numberOfLadders, false);
        } else {
            initializeFromConfig(snakes, true);
            initializeFromConfig(ladders, false);
        }
    }

    private void generateRandomPositions(int count, boolean isSnake) {
        while (count > 0) {
            int start = ThreadLocalRandom.current().nextInt(startPosition, size + 1);
            int end = ThreadLocalRandom.current().nextInt(startPosition, size + 1);
            if ((isSnake && end >= start) || (!isSnake && start >= end)) continue;

            if (addPosition(start, end, isSnake)) count--;
        }
    }

    private <T> void initializeFromConfig(List<T> elements, boolean isSnake) {
        for (T element : elements) {
            int start = isSnake ? ((Snake) element).head() : ((Ladder) element).bottom();
            int end = isSnake ? ((Snake) element).tail() : ((Ladder) element).top();
            addPosition(start, end, isSnake);
        }
    }

    private boolean addPosition(int start, int end, boolean isSnake) {
        String key = start + "-" + end;
        if (!snakeAndLadderSet.contains(key)) {
            if (isSnake) {
                snakeHeadPositions.put(start, end);
            } else {
                ladderBottomPositions.put(start, end);
            }
            snakeAndLadderSet.add(key);
            return true;
        }
        return false;
    }

    public int getNewPosition(int newPosition) {
        if (snakeHeadPositions.containsKey(newPosition)) {
            return snakeHeadPositions.get(newPosition);
        }

        if (ladderBottomPositions.containsKey(newPosition)) {
            return ladderBottomPositions.get(newPosition);
        }

        if(mine.containsKey(newPosition)) {

        }


        return newPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }
}
