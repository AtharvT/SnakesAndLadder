package org.snakesandladders.strategy;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinStrategyTest {

    @Test
    public void whenMultipleDiceRolls_thenReturnsMinimumValue() {
        MinStrategy strategy = new MinStrategy();
        int[] diceRolls = {4, 6, 2, 5};
        int result = strategy.execute(diceRolls);
        assertEquals(2, result, "Should return the minimum value from the dice rolls.");
    }

    @Test
    public void whenSingleDiceRoll_thenReturnsThatValue() {
        MinStrategy strategy = new MinStrategy();
        int[] diceRolls = {5};
        int result = strategy.execute(diceRolls);
        assertEquals(5, result, "Should return the single dice roll value.");
    }

    @Test
    public void whenEmptyDiceRolls_thenThrowsIllegalArgumentException() {
        MinStrategy strategy = new MinStrategy();
        int[] diceRolls = {};
        assertThrows(IllegalArgumentException.class, () -> strategy.execute(diceRolls),
                "Should throw IllegalArgumentException when diceRolls array is empty");
    }
}
