package org.snakesandladders.strategy;

import java.util.Arrays;

/**
 * Strategy that returns min value of dices
 */
public class MinStrategy implements MovementStrategy {
    @Override
    public int execute(int[] diceRolls) {
        return Arrays.stream(diceRolls).min().orElseThrow(() -> new IllegalArgumentException("diceRolls array cannot be empty"));
    }
}
