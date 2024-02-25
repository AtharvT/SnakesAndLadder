package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return head == snake.head && tail == snake.tail;
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail);
    }

    public boolean addSnakeIfValid(Set<Snake> snakeSet, Snake newSnake) {
        for (Snake existingSnake : snakeSet) {
            if (existingSnake.head == newSnake.head() ||
                    existingSnake.head() == newSnake.tail() ||
                    existingSnake.tail() == newSnake.head() ||
                    existingSnake.tail() == newSnake.tail()) {
                return false;
            }
        }
        return snakeSet.add(newSnake);
    }
}