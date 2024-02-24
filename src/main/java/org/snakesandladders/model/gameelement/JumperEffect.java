package org.snakesandladders.model.gameelement;

/**
 * record JumperEffect added to handle cases for mine and crocodile and abstract away the logic
 */
public record JumperEffect(Integer newPosition, Integer turnsHeld) {

    /**
     * Creates a JumperEffect to move the player to a new position.
     * @param newPosition The new position of the player.
     * @return A JumperEffect with the new position and zero turns held.
     */
    public static JumperEffect move(Integer newPosition) {
        return new JumperEffect(newPosition, 0);
    }

    /**
     * Creates a JumperEffect to hold the player for a specified number of turns.
     * @param turnsHeld The number of turns the player is held.
     * @return A JumperEffect with null newPosition and specified turnsHeld.
     */
    public static JumperEffect hold(Integer turnsHeld) {
        return new JumperEffect(null, turnsHeld);
    }
}