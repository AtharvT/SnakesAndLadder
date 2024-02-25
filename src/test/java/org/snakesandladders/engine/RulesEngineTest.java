package org.snakesandladders.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.snakesandladders.model.Board;
import org.snakesandladders.model.Player;
import org.snakesandladders.model.SnakesAndLadderBoard;

import java.util.HashMap;
import java.util.Map;

public class RulesEngineTest {

    private RulesEngine rulesEngine;
    private SnakesAndLadderBoard board;
    private Player player;

    @BeforeEach
    void setUp() {
        rulesEngine = new RulesEngine();
        board = mock(SnakesAndLadderBoard.class);
        player = mock(Player.class);
    }

    @Test
    void testValidateMove_ValidMove() {
        when(player.getPosition()).thenReturn(10);
        assertTrue(rulesEngine.validateMove(player), "Move should be valid for position greater than 0");
    }

    @Test
    void testValidateMove_InvalidMove() {
        when(player.getPosition()).thenReturn(-1);
        assertFalse(rulesEngine.validateMove(player), "Move should be invalid for position less than or equal to 0");
    }

    @Test
    void testCheckWinCondition_Win() {
        when(player.getPosition()).thenReturn(100);
        when(board.getSize()).thenReturn(100);
        assertTrue(rulesEngine.checkWinCondition(player, board), "Player should win if position is at least board size");
    }

    @Test
    void testCheckWinCondition_NotWin() {
        when(player.getPosition()).thenReturn(99);
        when(board.getSize()).thenReturn(100);
        assertFalse(rulesEngine.checkWinCondition(player, board), "Player should not win if position is less than board size");
    }

    @Test
    void testCheckForExistingPlayer_Exists() {
        Map<Integer, Player> positionToPlayer = new HashMap<>();
        positionToPlayer.put(10, player);
        assertTrue(rulesEngine.checkForExistingPlayer(10, positionToPlayer), "Should return true if a player exists at the given position");
    }

    @Test
    void testCheckForExistingPlayer_NotExists() {
        Map<Integer, Player> positionToPlayer = new HashMap<>();
        assertFalse(rulesEngine.checkForExistingPlayer(10, positionToPlayer), "Should return false if no player exists at the given position");
    }

    @Test
    void testValidate_ValidBoardAndPlayerCount() {
        when(board.getSize()).thenReturn(100);
        assertDoesNotThrow(() -> rulesEngine.validate(board, 2), "Should not throw exception for valid board and player count");
    }

    @Test
    void testValidate_InvalidBoardType() {
        Board invalidBoard = mock(Board.class); // Not a SnakesAndLadderBoard
        assertThrows(IllegalArgumentException.class, () -> rulesEngine.validate(invalidBoard, 2), "Should throw IllegalArgumentException for invalid board type");
    }

}
