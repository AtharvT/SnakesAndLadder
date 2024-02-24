package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

public record Ladder(int bottom, int top) implements GameElement {
    public Ladder {
        if (top <= bottom) {
            throw new IllegalArgumentException("Top of ladder must be higher than bottom.");
        }
    }
    @Override
    public Integer getStart() {
        return bottom;
    }

    @Override
    public JumperEffect applyEffect(Player player) {
        return JumperEffect.move(top);
    }

    @Override
    public Integer getEnd() {
        return top;
    }
}