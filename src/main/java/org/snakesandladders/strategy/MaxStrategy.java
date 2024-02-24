package org.snakesandladders.strategy;

import java.util.Arrays;

/**
 * Strategy that returns max value of dices
 */
public class MaxStrategy implements MovementStrategy {
    @Override
    public int execute(int[] diceRolls) {
        return Arrays.stream(diceRolls).max().orElseThrow(() -> new IllegalArgumentException("diceRolls array cannot be empty"));
    }
}