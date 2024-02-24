package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

public record Mine(int position) implements GameElement {
    @Override
    public Integer getStart() {
        return position;
    }

    @Override
    public JumperEffect applyEffect(Player player) {
        return JumperEffect.hold(2);
    }

    @Override
    public Integer getEnd() {
        return position;
    }
}
