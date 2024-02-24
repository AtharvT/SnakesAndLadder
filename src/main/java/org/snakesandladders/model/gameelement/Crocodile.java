package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

public record Crocodile(int currentPosition) implements GameElement{

    @Override
    public Integer getStart() {
        return currentPosition;
    }

    @Override
    public JumperEffect applyEffect(Player player) {

        int newPosition = Math.max(1, currentPosition - 5);
        return JumperEffect.move(newPosition); // Moves the player 5 positions back
    }

    @Override
    public Integer getEnd() {
        return currentPosition;
    }
}