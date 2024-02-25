package org.snakesandladders.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.snakesandladders.config.GameConfig;
import org.snakesandladders.model.Player;
import org.snakesandladders.model.SnakesAndLadderBoard;
import org.snakesandladders.model.gameelement.Ladder;
import org.snakesandladders.model.gameelement.Snake;
import org.snakesandladders.utils.InputHandler;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameEngineTest {

    @Mock
    private RulesEngine rulesEngine;
    @Mock
    private InputHandler inputHandler;

    private GameEngine gameEngine;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(inputHandler.askForManualRoll()).thenReturn(false);
        when(rulesEngine.validate(any(SnakesAndLadderBoard.class), anyInt())).thenReturn(true);
        gameEngine = new GameEngine(rulesEngine, inputHandler, false);
        GameConfig dummyConfig = new GameConfig();
        dummyConfig.setNumberOfSnakes(5);
        dummyConfig.setNumberOfLadders(5);
        dummyConfig.setBoardSize(100);
        dummyConfig.setNumberOfPlayers(2);
    }


    @Test
    void testGameInitialization() throws Exception {
        when(inputHandler.askForManualRoll()).thenReturn(false);
        assertNotNull(gameEngine);
        verify(rulesEngine, times(1)).validate(any(SnakesAndLadderBoard.class), anyInt());
    }

    @Test
    void testAddPlayer() {
        Player player = new Player("Test Player", 0);
        gameEngine.addPlayer(player);
        assertEquals(1, gameEngine.getPlayers().size());
    }


    @Test
    void testInvalidMove() {
        Player player = new Player("Test Player", 1);
        gameEngine.addPlayer(player);
        int initialPosition = player.getPosition();
        assertFalse(gameEngine.processPlayerMove(player, gameEngine.getBoard().getSize() + 1));
        assertEquals(initialPosition, player.getPosition()); // Player should not move
    }

    @Test
    void testGenerateSnakesAndLadders() {
        Set<Snake> snakes = gameEngine.generateSnakes(5, gameEngine.getBoard().getSize());
        Set<Ladder> ladders = gameEngine.generateLadders(5, gameEngine.getBoard().getSize());
        assertEquals(5, snakes.size());
        assertEquals(5, ladders.size());
    }

}