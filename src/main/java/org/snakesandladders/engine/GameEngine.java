package org.snakesandladders.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakesandladders.config.ConfigLoader;
import org.snakesandladders.config.DiceConfig;
import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.GameConfigLoadException;
import org.snakesandladders.exceptions.InvalidBoardException;
import org.snakesandladders.exceptions.InvalidGameElementException;
import org.snakesandladders.factory.BoardFactory;
import org.snakesandladders.model.*;
import org.snakesandladders.model.gameelement.*;
import org.snakesandladders.utils.InputHandler;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameEngine {
    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);
    private final Queue<Player> players;
    private final boolean manualMode;
    private Dice dice;
    private SnakesAndLadderBoard board;
    private final GameConfig config;
    private final DiceConfig diceConfig;
    Set<String> snakeAndLadderSet;
    InputHandler inputHandler;
    private final RulesEngine rulesEngine;
    private Map<Integer, Player> positionToPlayer = new HashMap<>();

    private static final String GAME_CONFIG_PATH = "/config/gameConfig.json";
    private static final String DICE_CONFIG_PATH = "/config/diceConfig.json";

    /**
     * Constructs a GameEngine instance with the specified rules engine, input handler, and mode.
     *
     * @param rulesEngine  The rules engine responsible for applying game rules.
     * @param inputHandler The input handler used for gathering input from players.
     * @param isManualMode Specifies whether the game should run in manual mode, allowing manual dice rolls and manual entry of data points
     * @throws GameConfigLoadException     If there is an issue loading the game configuration.
     * @throws InvalidGameElementException If the game elements (snakes, ladders) are invalid.
     */

    public GameEngine(RulesEngine rulesEngine, InputHandler inputHandler, boolean isManualMode) throws GameConfigLoadException, InvalidGameElementException, InvalidBoardException {
        this.rulesEngine = rulesEngine;
        this.players = new ArrayDeque<>();
        this.snakeAndLadderSet = new HashSet<>();
        this.inputHandler = inputHandler;
        this.config = ConfigLoader.loadConfig(GAME_CONFIG_PATH, GameConfig.class);
        this.diceConfig = ConfigLoader.loadConfig(DICE_CONFIG_PATH, DiceConfig.class);
        this.manualMode = isManualMode;
        this.board = initializeBoard();
        initializeGame();
        rulesEngine.validate(board, players.size());
    }

    public void initializeGame() throws InvalidGameElementException {
        if (this.manualMode) {
            setupManualGame();
        } else {
            setupConfigGame();
        }
        logger.info("Game initialized successfully.");
    }

    private SnakesAndLadderBoard initializeBoard() throws GameConfigLoadException {
        try {
            return (SnakesAndLadderBoard) BoardFactory.createBoard("SnakesAndLadders", config);
        } catch (InvalidGameElementException e) {
            logger.error("Failed to create game board: {}", e.getMessage());
            throw new GameConfigLoadException("Failed to initialize the game board.");
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playGame() {
        int turnCounter = 0;
        int maxTurns = 100;
        while (!players.isEmpty() && turnCounter < maxTurns) {
            turnCounter++;
            Player player = players.poll();
            assert player != null;

            if (player.isWaiting()) {
                System.out.println(player.getName() + " is waiting and skips a turn.");
                player.decrementWaitingTurn();
                continue;
            }
            int diceRoll = rollDice();
            if (!processPlayerMove(player, diceRoll)) {
                continue;
            }
            if (rulesEngine.checkWinCondition(player, board)) {
                player.setWon(true);
                System.out.println("Player " + player.getName() + " Won.");
                break;
            }
            players.offer(player);
        }
        if (turnCounter >= maxTurns) {
            System.out.println("Game ended due to reaching the maximum number of turns.");
        }
    }


    /**
     * Generates a set of snakes for the game board.
     * Each snake has a head and a tail, with the head positioned at a higher number than the tail.
     *
     * @param numberOfSnakes The number of snakes to generate.
     * @param boardSize      The size of the game board.
     * @return A set of Snake objects.
     */
    public Set<Snake> generateSnakes(int numberOfSnakes, int boardSize) {
        Set<Snake> snakes = new HashSet<>();
        for (int i = 0; i < numberOfSnakes; i++) {
            int head, tail;
            do {
                head = ThreadLocalRandom.current().nextInt(2, boardSize); // Avoid the first cell for the head
                tail = ThreadLocalRandom.current().nextInt(1, head); // Ensure tail is before head
            } while (!isValid(head, tail, snakes));
            var newSnake = new Snake(head, tail);
           if(newSnake.addSnakeIfValid(snakes, newSnake)) {
               snakeAndLadderSet.add(tail+"-"+head);
           }

        }
        return snakes;
    }

    /**
     * Generates a set of ladder for the game board.
     * Each ladder has a bottom and a top, with the top positioned at a higher number than the bottom.
     *
     * @param numberOfLadders The number of snakes to generate.
     * @param boardSize       The size of the game board.
     * @return A set of Snake objects.
     */
    public Set<Ladder> generateLadders(int numberOfLadders, int boardSize) {
        Set<Ladder> ladders = new HashSet<>();
        for (int i = 0; i < numberOfLadders; i++) {
            int bottom, top;
            do {
                bottom = ThreadLocalRandom.current().nextInt(1, boardSize - 1); // Avoid the last cell for bottom
                top = ThreadLocalRandom.current().nextInt(bottom + 1, boardSize); // Ensure top is after bottom
            } while (!isValid(bottom, top, ladders));
            var newLadder = new Ladder(bottom, top);
            if(newLadder.addLadderIfValid(ladders, newLadder)) {
                snakeAndLadderSet.add(bottom+"-"+top);
            }
        }

        return ladders;
    }

    /**
     * Checks if the proposed start and end positions for a game element (snake, ladder, etc.) are valid.
     * Validation ensures that the new game element does not overlap with existing elements on the board
     * and adheres to the game's rules. For snakes and ladders, it checks that their positions do not
     * conflict with each other or with other special game elements like crocodiles and mines.
     *
     * @param start    The starting position of the new game element. For snakes, this is the head position,
     *                 and for ladders, it's the bottom position.
     * @param end      The ending position of the new game element. For snakes, this is the tail position,
     *                 and for ladders, it's the top position.
     * @param elements The list of existing game elements to check against for overlaps or conflicts.
     * @return true if the proposed positions do not conflict with any existing game elements, false otherwise.
     */
    private boolean isValid(int start, int end, Set<? extends GameElement> elements) {
        return elements.stream().noneMatch(element -> {
            if (element instanceof Snake || element instanceof Ladder) {
                return element.getStart() == start || element.getEnd() == end || element.getStart() == end || element.getEnd() == start || snakeAndLadderSet.contains(start + "-" + end);
            } else {
                return element.getStart() == start;
            }
        });
    }

    /**
     * Rolls the dice to determine the number of steps a player can move.
     * The roll can be manual based on player input or automatic based on random generation, depending on the game mode.
     *
     * @return The result of the dice roll based on the strategy asssigned in configuration
     */
    public int rollDice() {
        int val = 0;
        if (inputHandler.askForManualRoll()) {
            int[] manualValues = inputHandler.getManualDiceRolls(dice.getNumberOfDice());
            val = dice.manualRoll(manualValues);
        } else {
            val = dice.roll();
        }
        return val;
    }

    private void setupManualGame() {
        config.setNumberOfSnakes(inputHandler.getNumberOfSnakes(config.getBoardSize()));
        config.setSnakeList(inputHandler.getSnakePositions(config.getNumberOfSnakes(), snakeAndLadderSet));
        config.setNumberOfLadders(inputHandler.getNumberOfLadders(config.getBoardSize()));
        config.setLadderList(inputHandler.getLadderPositions(config.getNumberOfLadders(), snakeAndLadderSet));
        config.setNumberOfPlayers(inputHandler.addPlayers(players, -1));
        this.dice = inputHandler.getDice(config.getMovementStrategy());
    }

    private void setupConfigGame() {
        this.dice = new Dice(diceConfig.getDiceMinValue(), diceConfig.getDiceMaxValue(), config.getNumberOfDies(), config.getMovementStrategy());
        config.setSnakeList(generateSnakes(config.getNumberOfSnakes(), config.getBoardSize()));
        config.setLadderList(generateLadders(config.getNumberOfLadders(), config.getBoardSize()));
        inputHandler.addPlayers(players, config.getNumberOfPlayers());
    }

    /**
     * processes the player move and checks for its feasibility
     *
     * @return boolean false if it is not possible otherwise true
     */
    private boolean processPlayerMove(Player player, int diceRoll) {
        int oldPosition = player.getPosition();
        int newPosition = player.getPosition() + diceRoll;
        player.setPosition(board.getNewPosition(newPosition, player));
        System.out.println(player.getName() + " rolled a  " + diceRoll + " and moved from " + oldPosition + " to " + player.getPosition());

        if (!rulesEngine.validateMove(player)) {

            System.out.println("Move not allowed, kindly retry");
            player.setPosition(oldPosition);
            players.offer(player);
            return false;
        }

        if (rulesEngine.checkForExistingPlayer(newPosition, positionToPlayer)) {
            System.out.println(player.getName() + " rolled a  " + diceRoll + " and moved from " + oldPosition + " to " + player.getPosition() + " but a player is already there. Hence moving to position 1");
            player.setPosition(1);
        }
        updatePlayerPosition(player, newPosition);

        return true;
    }

    private void updatePlayerPosition(Player player, int newPosition) {
        positionToPlayer.values().remove(player);
        player.setPosition(newPosition);
        positionToPlayer.put(newPosition, player);
    }
}

