package org.snakesandladders.strategy;

import org.junit.jupiter.api.Test;
import org.snakesandladders.strategy.SumStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumStrategyTest {

    @Test
    public void whenMultipleDiceRolls_thenReturnsSum() {
        SumStrategy strategy = new SumStrategy();
        int[] diceRolls = {4, 6, 2, 5};
        int result = strategy.execute(diceRolls);
        assertEquals(17, result, "Should return the sum of all dice rolls.");
    }

    @Test
    public void whenSingleDiceRoll_thenReturnsThatValue() {
        SumStrategy strategy = new SumStrategy();
        int[] diceRolls = {5};
        int result = strategy.execute(diceRolls);
        assertEquals(5, result, "Should return the single dice roll value as the sum.");
    }

    @Test
    public void whenEmptyDiceRolls_thenReturnsZero() {
        SumStrategy strategy = new SumStrategy();
        int[] diceRolls = {};
        int result = strategy.execute(diceRolls);
        assertEquals(0, result, "Should return 0 for an empty array of dice rolls.");
    }
}
