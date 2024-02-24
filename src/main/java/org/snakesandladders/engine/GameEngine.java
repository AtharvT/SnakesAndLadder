package org.snakesandladders.engine;

import org.snakesandladders.config.ConfigLoader;
import org.snakesandladders.config.DiceConfig;
import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.GameConfigLoadException;
import org.snakesandladders.exceptions.InvalidGameElementException;
import org.snakesandladders.factory.BoardFactory;
import org.snakesandladders.model.*;
import org.snakesandladders.model.gameelement.*;
import org.snakesandladders.utils.InputHandler;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameEngine {

    private final Queue<Player> players;
    private final boolean manualMode;
    private Dice dice;
    private SnakesAndLadderBoard board;
    private final GameConfig config;
    private final DiceConfig diceConfig;
    Set<String> snakeAndLadderSet;
    InputHandler inputHandler;
    private final RulesEngine rulesEngine;

    private static final String GAME_CONFIG_PATH = "/config/gameConfig.json";
    private static final String DICE_CONFIG_PATH = "/config/diceConfig.json";


    public GameEngine(RulesEngine rulesEngine, InputHandler inputHandler, boolean isManualMode) throws GameConfigLoadException, InvalidGameElementException {
        this.rulesEngine = rulesEngine;
        this.players = new ArrayDeque<>();
        this.snakeAndLadderSet = new HashSet<>();
        this.inputHandler = inputHandler;
        this.config = ConfigLoader.loadConfig(GAME_CONFIG_PATH, GameConfig.class);
        this.diceConfig = ConfigLoader.loadConfig(DICE_CONFIG_PATH, DiceConfig.class);
        this.manualMode = isManualMode;
        try {
            initializeGame();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            initializeGame();
        }
//        rulesEngine.checkIfValidBoard(board);

    }

    public void initializeGame() throws InvalidGameElementException {

        if (this.manualMode) {
            config.setNumberOfSnakes(inputHandler.getNumberOfSnakes(config.getBoardSize()));
            config.setSnakeList(inputHandler.getSnakePositions(config.getNumberOfSnakes()));
            config.setNumberOfLadders(inputHandler.getNumberOfLadders(config.getBoardSize()));
            config.setLadderList(inputHandler.getLadderPositions(config.getNumberOfLadders()));
            config.setNumberOfPlayers(inputHandler.addPlayers(players, -1));
            this.dice = inputHandler.getDice(config.getMovementStrategy());
        } else {
            this.dice = new Dice(diceConfig.getDiceMinValue(), diceConfig.getDiceMaxValue(), config.getNumberOfDies(), config.getMovementStrategy());
            config.setSnakeList(generateSnakes(config.getNumberOfSnakes(), config.getBoardSize()));
            config.setLadderList(generateLadders(config.getNumberOfLadders(), config.getBoardSize()));
            inputHandler.addPlayers(players, config.getNumberOfPlayers());
        }
        this.board = (SnakesAndLadderBoard) BoardFactory.createBoard("SnakesAndLadders", config);

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playGame() {
        while (!players.isEmpty()) {
            Player player = players.poll();


            if (player.isWaiting()) {
                player.setRemainingWaitingTurns(player.getRemainingWaitingTurns() - 1);
                players.offer(player);
                continue;
            }


            int val = rollDice();
            int oldPosition = player.getPosition();
            int newPosition = player.getPosition() + val;


            player.setPosition(board.getNewPosition(newPosition, player));

            System.out.println(player.getName() + " rolled a  " + val + " and moved from " + oldPosition + " to " + player.getPosition());
            if (!rulesEngine.validateMove(player, board)) {

                System.out.println("Move not allowed, kindly retry");
                player.setPosition(oldPosition);
                players.offer(player);
                continue;
            }

            if (rulesEngine.checkWinCondition(player, board)) {
                System.out.println("Player " + player.getName() + " Won.");
                break;
            } else {

                if (rulesEngine.checkForExistingPlayer(newPosition)) {
                    System.out.println(player.getName() + " rolled a  " + val + " and moved from " + oldPosition + " to " + player.getPosition() + " but a player is already there. Hence moving to position 1");
                    player.setPosition(1);
                }
                rulesEngine.removeFromOldPosition(oldPosition);
                rulesEngine.updatePositionToPlayer(player);
            }
            players.offer(player);
        }
    }

    public List<Snake> generateSnakes(int numberOfSnakes, int boardSize) {
        List<Snake> snakes = new ArrayList<>();
        for (int i = 0; i < numberOfSnakes; i++) {
            int head, tail;
            do {
                head = ThreadLocalRandom.current().nextInt(2, boardSize); // Avoid the first cell for the head
                tail = ThreadLocalRandom.current().nextInt(1, head); // Ensure tail is before head
            } while (!isValid(head, tail, snakes));
            snakes.add(new Snake(head, tail));
        }
        return snakes;
    }

    public List<Ladder> generateLadders(int numberOfLadders, int boardSize) {
        List<Ladder> ladders = new ArrayList<>();
        for (int i = 0; i < numberOfLadders; i++) {
            int bottom, top;
            do {
                bottom = ThreadLocalRandom.current().nextInt(1, boardSize - 1); // Avoid the last cell for bottom
                top = ThreadLocalRandom.current().nextInt(bottom + 1, boardSize); // Ensure top is after bottom
            } while (!isValid(bottom, top, ladders));
            ladders.add(new Ladder(bottom, top));
        }
        return ladders;
    }


    private boolean isValid(int start, int end, List<? extends GameElement> elements) {
        for (GameElement element : elements) {
            if (element instanceof Snake) {
                Snake snake = (Snake) element;
                if (snake.head() == start || snake.tail() == end || snake.head() < snake.tail()) return false;
            } else if (element instanceof Ladder) {
                Ladder ladder = (Ladder) element;
                if (ladder.bottom() == start || ladder.top() == end || ladder.bottom() > ladder.top()) return false;
            } else if (element instanceof Crocodile) {
                Crocodile crocodile = (Crocodile) element;
                if (crocodile.getStart() == start) {
                    return false;
                }
            } else if (element instanceof Mine) {
                Mine mine = (Mine) element;
                if (mine.getStart() == start) {
                    return false;

                }
            }
        }
        return true;
    }

    public int rollDice() {
         int val = 0;
        if (inputHandler.askForManualRoll()) {
            int[] manualValues = inputHandler.getManualDiceRolls(config.getNumberOfDies());
            val = dice.manualRoll(manualValues);
        } else {
            val = dice.roll();
        }
        return val;
    }
}

