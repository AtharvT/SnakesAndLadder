package org.snakesandladders.utils;

import org.snakesandladders.model.Dice;
import org.snakesandladders.model.Player;
import org.snakesandladders.model.gameelement.Ladder;
import org.snakesandladders.model.gameelement.Snake;
import org.snakesandladders.strategy.MovementStrategy;

import java.util.*;

/**
 * Handles user input for the Snakes and Ladders game, including game configuration, player actions, and manual dice rolls.
 */
public class InputHandler {
    private final Scanner scanner;

    /**
     * Constructs an InputHandler with a new Scanner instance for reading console input.
     */
    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user to decide whether to continue playing the game.
     *
     * @return true if the user wants to continue playing, false otherwise.
     */
    public boolean isContinueGame() {
        System.out.println("Do you want to continue playing?");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            switch (input) {
                case "YES", "Y" -> {
                    return true;
                }
                case "NO", "N" -> {
                    return false;
                }
                default -> System.out.println("Invalid input. Please type 'YES'/'NO");
            }

        }
    }

    /**
     * Gets manual dice roll values from the user based on the specified number of dice.
     *
     * @param numberOfDice The number of dice to roll manually.
     * @return An array of integers representing the manual roll values for each die.
     * <p>
     * This is based on my interpretation of the manual override function that is expected
     */
    public int[] getManualDiceRolls(int numberOfDice) {
        int[] rolls = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            System.out.println("Enter the manual value for dice " + (i + 1) + ":");
            while (true) {
                try {
                    rolls[i] = Integer.parseInt(scanner.nextLine()); // Use nextLine and parse to int to handle invalid inputs better
                    if (rolls[i] < 1 || rolls[i] > 6) { // Assuming dice values range from 1 to 6
                        System.out.println("Out of range, please enter a value between 1 and 6:");
                    } else {
                        break; // Break the loop if the input is valid
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 6:");
                }
            }
        }
        return rolls;
    }


    /**
     * Asks the user if they would like to roll the dice manually.
     *
     * @return true if the user opts for manual dice rolls, false for automatic rolls.
     */
    public boolean askForManualRoll() {
        while (true) { // Loop until valid input is received
            System.out.println("Would you like to roll the dice manually? (Y/N)");
            String ans = scanner.nextLine().trim().toUpperCase();
            switch (ans) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Invalid input. Please type 'Y' for yes or 'N' for no.");
                    // The loop will continue, prompting the user again
            }
        }
    }

    /**
     * Collects dice configuration from the user including the number of dice and their min/max values,
     * and creates a Dice instance based on the input.
     *
     * @param movementStrategy The movement strategy to be applied to the dice rolls which is as of now taken from the config
     * @return A new Dice object configured according to the user's input.
     */
    public Dice getDice(MovementStrategy movementStrategy) {
        int numberOfDice = 0, minValue = 0, maxValue = 0;
        while (true) {
            try {
                System.out.println("Enter the number of dice: ");
                numberOfDice = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Enter minValue on dice:");
                minValue = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Enter maxValue:");
                maxValue = Integer.parseInt(scanner.nextLine().trim());

                if (numberOfDice <= 0 || minValue <= 0 || maxValue <= 0) {
                    System.out.println("The number of dice and value ranges must be positive. Please try again.");
                } else if (minValue >= maxValue) {
                    System.out.println("MaxValue must be greater than MinValue. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            }
        }
        return new Dice(minValue, maxValue, numberOfDice, movementStrategy);
    }


    /**
     * Prompts the user to enter the number of snakes for the game board, ensuring it's within valid bounds.
     *
     * @param boardSize The size of the game board, used to validate the number of snakes.
     * @return The number of snakes as entered by the user.
     */
    public int getNumberOfSnakes(int boardSize) {
        int numberOfSnakes;
        System.out.println("Enter the number of snakes: ");
        while (true) {
            try {
                numberOfSnakes = Integer.parseInt(scanner.nextLine());
                if (numberOfSnakes < 0) {
                    System.out.println("The number of snakes cannot be negative. Please enter a positive integer or 0:");
                } else if (numberOfSnakes >= boardSize) {
                    System.out.println("The number of snakes cannot be greater than the boardSize. Please retry");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer or 0:");
            }
        }
        return numberOfSnakes;
    }

    /**
     * Prompts the user to enter the number of ladders for the game board, ensuring it's within valid bounds.
     *
     * @param boardSize The size of the game board, used to validate the number of ladders.
     * @return The number of ladders as entered by the user.
     */
    public int getNumberOfLadders(int boardSize) {
        int numberOfLadders;
        System.out.println("Enter the number of ladders: ");
        while (true) {
            try {
                numberOfLadders = Integer.parseInt(scanner.nextLine());
                if (numberOfLadders < 0) {
                    System.out.println("The number of ladders cannot be negative. Please enter a positive integer or 0:");
                } else if (numberOfLadders >= boardSize) {
                    System.out.println("The number of ladders cannot be greater than the boardSize. Please retry");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer or 0:");
            }
        }
        return numberOfLadders;
    }

    /**
     * Queries the user for manual or automatic game settings mode.
     *
     * @return true if the user opts for manual mode, false for automatic mode.
     */
    public boolean isManualMode() {
        System.out.println("Do you want to enter the game settings manually? Type 'YES' for manual mode or 'NO' to load from config: ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase(); // Convert to upper case
            switch (input) {
                case "YES", "Y" -> {
                    return true;
                }
                case "NO", "N" -> {
                    return false;
                }
                default ->
                        System.out.println("Invalid input. Please type 'YES' for manual mode or 'NO' to load from config: ");
            }

        }
    }

    /**
     * Collects positions for snakes based on the specified number and ensures they are valid.
     *
     * @param numberOfSnakes The number of snakes to be added to the game board.
     * @return A set of Snake objects with positions set according to user input.
     */
    public Set<Snake> getSnakePositions(int numberOfSnakes, Set<String> snakeAndLadderSet) {
        Set<Snake> snakeList = new HashSet<>();
        for (int i = 1; i <= numberOfSnakes; i++) {
            while (true) {
                System.out.print("Enter head and tail positions for snake " + i + " (head > tail): ");
                String input = scanner.nextLine();
                String[] parts = input.split(" ");

                if (parts.length != 2) {
                    System.out.println("Invalid format. Please enter the head and tail positions separated by a space.");
                    continue;
                }

                try {
                    int head = Integer.parseInt(parts[0]);
                    int tail = Integer.parseInt(parts[1]);

                    if (head <= tail) {
                        System.out.println("Invalid positions. The head position must be greater than the tail position. Please, try again.");
                    } else {
                        Snake snake = new Snake(head, tail);
                        if(!snake.addSnakeIfValid(snakeList,snake) || snakeAndLadderSet.contains(snake.head()+"-"+snake.tail())) {
                            System.out.println("Duplicate entry for snake not allowed please try again");
                        }
                        else {
                            snakeAndLadderSet.add(snake.head()+"-"+snake.tail());
                            break;
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter two integers separated by a space.");
                }
            }
        }
        return snakeList;
    }

    /**
     * Collects positions for ladders based on the specified number and ensures they are valid.
     *
     * @param numberOfLadders The number of ladders to be added to the game board.
     * @return A list of Ladder objects with positions set according to user input.
     */
    public Set<Ladder> getLadderPositions(int numberOfLadders, Set<String> snakeAndLadderSet) {
        Set<Ladder> ladderList = new HashSet<>();
        for (int i = 1; i <= numberOfLadders; i++) {
            while (true) {
                System.out.print("Enter bottom and top positions for ladder " + i + " (bottom < top): ");
                String input = scanner.nextLine();
                String[] parts = input.split(" ");

                if (parts.length != 2) {
                    System.out.println("Invalid format. Please enter the bottom and top positions separated by a space.");
                    continue;
                }
                try {
                    int bottom = Integer.parseInt(parts[0]);
                    int top = Integer.parseInt(parts[1]);

                    if (bottom >= top) {
                        System.out.println("Invalid positions. The bottom position must be less than the top position. Please, try again.");
                    } else {
                        Ladder ladder = new Ladder(bottom, top);
                        if(!ladder.addLadderIfValid(ladderList, ladder) || snakeAndLadderSet.contains(ladder.top()+"-"+ladder.bottom())) {
                            System.out.println("Duplicate entry for ladder not allowed please try again");
                        }
                        else {
                            snakeAndLadderSet.add(ladder.top()+"-"+ladder.bottom());
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter two integers separated by a space.");
                }
            }
        }
        return ladderList;
    }

    /**
     * Adds players to the game based on user input or configuration.
     *
     * @param players      The player queue to which new players will be added.
     * @param configPlayer The number of players specified in the configuration, or -1 if not specified.
     * @return The total number of players added to the game.
     */
    public int addPlayers(Queue<Player> players, int configPlayer) {
        int numberOfPlayers = 0;
        if (configPlayer != -1) {
            numberOfPlayers = configPlayer;
        } else {
            while (true) {
                System.out.print("Enter the number of players: ");
                String input = scanner.nextLine().trim();
                try {
                    numberOfPlayers = Integer.parseInt(input);
                    if (numberOfPlayers <= 0) {
                        System.out.println("The number of players must be greater than 0. Please try again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }
        }

        int count = 0;
        while (count < numberOfPlayers) {
            System.out.println("Enter name and starting location for player " + (count + 1) + " (format: Name Location):");
            String playerInput = scanner.nextLine();
            String[] parts = playerInput.split(" ", 2);

            if (parts.length == 2) {
                try {
                    String name = parts[0];
                    int startingLocation = Integer.parseInt(parts[1]);
                    if (startingLocation < 0) {
                        System.out.println("Starting location must be a non-negative integer. Please try again.");
                        continue;
                    }
                    players.offer(new Player(name, startingLocation));
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for starting location. Please enter a valid integer.");
                }
            } else {
                System.out.println("Invalid input format. Please try again using the format: Name Location");
            }
        }
        return numberOfPlayers;
    }


    public void close() {
        scanner.close();
    }
}
