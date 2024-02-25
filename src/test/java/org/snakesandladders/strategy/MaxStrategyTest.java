package org.snakesandladders.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxStrategyTest {

    @Test
    public void whenMultipleDiceRolls_thenReturnsMaxValue() {
        MaxStrategy strategy = new MaxStrategy();
        int[] diceRolls = {1, 3, 6, 4};
        int result = strategy.execute(diceRolls);
        assertEquals(6, result, "Should return the maximum value from the dice rolls.");
    }

    @Test
    public void whenSingleDiceRoll_thenReturnsThatValue() {
        MaxStrategy strategy = new MaxStrategy();
        int[] diceRolls = {3};
        int result = strategy.execute(diceRolls);
        assertEquals(3, result, "Should return the single dice roll value.");
    }

    @Test
    public void whenEmptyDiceRolls_thenThrowsException() {
        MaxStrategy strategy = new MaxStrategy();
        int[] diceRolls = {};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> strategy.execute(diceRolls), "Should throw IllegalArgumentException for an empty array.");
        assertEquals("diceRolls array cannot be empty", exception.getMessage(), "Exception message should match expected text.");
    }
}
