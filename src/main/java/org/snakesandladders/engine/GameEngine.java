package org.snakesandladders.engine;

import org.snakesandladders.config.GameConfig;
import org.snakesandladders.factory.BoardFactory;
import org.snakesandladders.model.*;
import org.snakesandladders.utils.InputHandler;

import java.util.*;

public class GameEngine {
    private final int numberOfDice;
    private final Queue<Player> players;
    private final Dice dice;
    private SnakesAndLadderBoard board;
    private final GameConfig config;

    Map<Integer, Player> positionToPlayer;
    Set<String> snakeAndLadderSet;
    InputHandler inputHandler;

    public GameEngine(int boardSize, int numberOfDice, GameConfig config) {
        this.numberOfDice = numberOfDice;
        this.players = new ArrayDeque<>();
        this.dice = new Dice(1, 6, numberOfDice);
        this.positionToPlayer = new HashMap<>();
        this.snakeAndLadderSet = new HashSet<>();
        this.inputHandler = new InputHandler();
        this.config = config;
        validateGameSetup();
        initGame(boardSize);
    }

    public void initGame(int boardSize) {
        boolean loadFromConfig = inputHandler.loadFromConfig();
        this.board = (SnakesAndLadderBoard) BoardFactory.createBoard("SnakesAndLadders", boardSize * boardSize, config, loadFromConfig);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playGame() {
        while (true) {
            Player player = players.poll();
            int val = 0;
            if (inputHandler.askForManualRoll()) {
                int[] manualValues = inputHandler.getManualDiceRolls(numberOfDice);
                val = dice.manualRoll(manualValues);
            } else {
                val = dice.roll();
            }
            int oldPosition = player.getPosition();
            int newPosition = player.getPosition() + val;

            if (newPosition >= board.getEndPosition()) {
                System.out.println("Player " + player.getName() + " Won.");
                break;
            } else {
                player.setPosition(board.getNewPosition(newPosition));
                System.out.println(player.getName() + " rolled a  " + val + " and moved from " + oldPosition + " to " + player.getPosition());
                if (positionToPlayer.containsKey(newPosition)) {
                    System.out.println(player.getName() + " rolled a  " + val + " and moved from " + oldPosition + " to " + player.getPosition() + " but a player is already there. Hence moving to position 1");
                    player.setPosition(1);
                }
                players.offer(player);
            }
        }
    }

    private void validateGameSetup() {
        if (config.getNumberOfPlayers() < 2) {
            throw new IllegalStateException("Not enough players to start the game");
        }
    }
}

