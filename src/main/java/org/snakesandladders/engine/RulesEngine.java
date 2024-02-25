package org.snakesandladders.engine;

import org.snakesandladders.exceptions.GameConfigLoadException;
import org.snakesandladders.exceptions.InvalidBoardException;
import org.snakesandladders.model.Board;
import org.snakesandladders.model.Player;
import org.snakesandladders.model.SnakesAndLadderBoard;
import org.snakesandladders.model.gameelement.GameElement;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RulesEngine {

    public RulesEngine() {
    }
    public boolean validateMove(Player player) {
        return player.getPosition() > 0;
    }

    public boolean checkWinCondition(Player player, Board board) {
        return player.getPosition() >= board.getSize();
    }

    public boolean checkForExistingPlayer(int newPosition, Map<Integer, Player> positionToPlayer) {
        return positionToPlayer.containsKey(newPosition);
    }


    /**
     * Validates the game board and player count to ensure they meet the requirements for a valid Snakes and Ladders game.
     * This method checks that the board is of the correct type, verifies the minimum number of players,
     * and ensures that no game elements on the board overlap in an invalid manner.
     *
     * @param board The game board to be validated. This must be an instance of {@link SnakesAndLadderBoard}.
     * @param playerCount The number of players participating in the game. There must be at least 2 players.
     * @throws GameConfigLoadException If there is an error loading the game configuration.
     * @throws InvalidBoardException If the board does not meet the validation criteria, including if it's not an instance
     *         of {@link SnakesAndLadderBoard}, if there are fewer than 2 players, or if any game elements (snakes, ladders, etc.)
     *         overlap in a manner that's not allowed by the game rules.
     * @throws IllegalArgumentException If the provided board is not of the correct type (i.e., not a {@link SnakesAndLadderBoard}).
     */
    public void validate(Board board, int playerCount) throws GameConfigLoadException, InvalidBoardException {
        if (!(board instanceof SnakesAndLadderBoard)) {
            throw new IllegalArgumentException("Invalid board type.Cannot validate at the moment");
        }

        if (playerCount < 2) {
            throw new InvalidBoardException("Number of players shall be atleast 2 to play the game");
        }
        List<GameElement> gameElements = ((SnakesAndLadderBoard) board).getGameElements();
        for (int i = 0; i < gameElements.size(); i++) {
            for (int j = i + 1; j < gameElements.size(); j++) {
                GameElement firstElement = gameElements.get(i);
                GameElement secondElement = gameElements.get(j);

                if (elementsOverlap(firstElement, secondElement)) {
                    throw new InvalidBoardException("Game elements on the board are over lapping");
                }
            }
        }
    }

    /**
     * Determines if two game elements overlap based on their positions.
     * This method needs to be adapted based on how GameElement class is structured.
     *
     * @param firstElement  The first game element.
     * @param secondElement The second game element.
     * @return true if the elements overlap, false otherwise.
     */
    private boolean elementsOverlap(GameElement firstElement, GameElement secondElement) {
        return Objects.equals(firstElement.getStart(), secondElement.getStart()) ||
                Objects.equals(firstElement.getEnd(), secondElement.getEnd());
    }

}
