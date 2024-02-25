package org.snakesandladders.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.InvalidGameElementException;
import org.snakesandladders.model.gameelement.Ladder;
import org.snakesandladders.model.gameelement.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SnakesAndLadderBoardTest {
    private GameConfig config;
    private SnakesAndLadderBoard board;
    @BeforeEach
    void setUp() throws InvalidGameElementException {
        config = new GameConfig();
        config.setBoardSize(100);
        config.setNumberOfPlayers(2);
        config.setSnakeList(Set.of(new Snake(14, 7)));
        config.setLadderList(Set.of(new Ladder(4, 25)));
        board = new SnakesAndLadderBoard(config);
    }

    @Test
    void testBoardInitialization() {
        Assertions.assertEquals(100*100, board.getSize(), "Board size*BoardSize should be actual size");
        board.getNewPosition(14, new Player("Test Player", 14));
        Assertions.assertNotNull(board.getNewPosition(4, new Player("Test Player", 4)), "Ladder should be at position 4.");
    }
}