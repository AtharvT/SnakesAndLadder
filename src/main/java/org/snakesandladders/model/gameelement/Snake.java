package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

public record Snake(int head, int tail) implements GameElement {
    public Snake {
        if (head <= tail) {
            throw new IllegalArgumentException("Head of snake must be higher than tail.");
        }
    }

    /**
     * Snake returning head and Ladder returning top is deliberate as while setting manually we would like to check
     * whether they are in same position on board or not
     */
    @Override
    public Integer getStart() {
        return head;
    }

    @Override
    public JumperEffect applyEffect(Player player) {
        return JumperEffect.move(tail);
    }

    @Override
    public Integer getEnd() {
        return tail;
    }
}