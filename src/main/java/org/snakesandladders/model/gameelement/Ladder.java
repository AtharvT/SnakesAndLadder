package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

import java.util.Objects;
import java.util.Set;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ladder)) return false;
        Ladder ladder = (Ladder) o;
        return bottom == ladder.bottom && top == ladder.top;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottom, top);
    }

    public boolean addLadderIfValid(Set<Ladder> ladderSet, Ladder newLadder) {
        for (Ladder existingLadder : ladderSet) {
            boolean isOverlapping =
                    newLadder.bottom() == existingLadder.bottom() ||
                            newLadder.top() == existingLadder.top() ||
                            newLadder.bottom() == existingLadder.top() ||
                            newLadder.top() == existingLadder.bottom();

            if (isOverlapping) {
                return false;
            }
        }
        return ladderSet.add(newLadder);
    }

}