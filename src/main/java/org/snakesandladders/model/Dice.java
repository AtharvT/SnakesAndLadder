package org.snakesandladders.model;

import org.snakesandladders.strategy.MovementStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private final int minValue;
    private final int maxValue;

    private final int numberOfDice;
    private MovementStrategy movementStrategy;

    public Dice(int minValue, int maxValue, int numberOfDice, MovementStrategy movementStrategy) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.numberOfDice = numberOfDice;
        this.movementStrategy = movementStrategy;
    }

    public int roll() {
        int[] rolls = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            rolls[i] = ThreadLocalRandom.current().nextInt(minValue, maxValue + 1);
        }
        return movementStrategy.execute(rolls);
    }

    public int manualRoll(int[] manualValues) {

        if (manualValues.length > numberOfDice) {
            throw new IllegalArgumentException("Cannot have more than " + numberOfDice + " dice rolls.");
        }
        return movementStrategy.execute(manualValues);
    }
    public int getNumberOfDice() {
        return numberOfDice;
    }
}
