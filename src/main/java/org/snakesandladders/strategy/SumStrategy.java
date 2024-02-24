package org.snakesandladders.strategy;

import java.util.Arrays;

/**
 * Strategy that calculates the sum of all dice rolls.
 */
public class SumStrategy implements MovementStrategy {
    @Override
    public int execute(int[] diceRolls) {
        return Arrays.stream(diceRolls).sum();
    }
}
