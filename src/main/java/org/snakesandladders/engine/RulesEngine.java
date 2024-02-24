package org.snakesandladders.engine;

import org.snakesandladders.model.Board;
import org.snakesandladders.model.Player;

import java.util.HashMap;
import java.util.Map;

public class RulesEngine {
    Map<Integer, Player> positionToPlayer;

    public RulesEngine() {
        this.positionToPlayer = new HashMap<>();
    }

    public boolean validateMove(Player player, Board board) {
        return player.getPosition() > 0 && player.getPosition() <= board.getSize();
    }

    public boolean checkWinCondition(Player player, Board board) {
        return player.getPosition() == board.getSize();
    }

    public boolean checkForExistingPlayer(int newPosition) {
        return positionToPlayer.containsKey(newPosition);
    }

    public void updatePositionToPlayer(Player player) {
        positionToPlayer.put(player.getPosition(), player);
    }

    public void removeFromOldPosition(int oldPosition) {
        positionToPlayer.remove(oldPosition);
    }

//    public boolean validate(Board board) {
//        if(board instanceof SnakesAndLadderBoard) {
//            board = (SnakesAndLadderBoard) board;
//            board.
//
//        }
//    }
}
