package org.snakesandladders.model.gameelement;

import org.snakesandladders.model.Player;

public interface GameElement {
    Integer getStart();
    JumperEffect applyEffect(Player player);

    Integer getEnd();
}
