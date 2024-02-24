package org.snakesandladders.utils;

import org.snakesandladders.model.Dice;
import org.snakesandladders.model.Player;
import org.snakesandladders.model.gameelement.Ladder;
import org.snakesandladders.model.gameelement.Snake;
import org.snakesandladders.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

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
                default ->
                        System.out.println("Invalid input. Please type 'YES'/'NO");
            }

        }
    }

    public int[] getManualDiceRolls(int numberOfDice) {
        int[] rolls = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            System.out.println("Enter the manual value for dice " + (i + 1) + ":");
            rolls[i] = scanner.nextInt();

            if(rolls[i] < 0 || rolls[i] > 6) {
                System.out.println("Out of range, please try again");  {
                    rolls[i] = scanner.nextInt();
                }
            }
        }
        scanner.nextLine(); // Consume newline left-over
        return rolls;
    }

    public boolean askForManualRoll() {
        System.out.println("Would you like to roll the dice manually? (Y/N)");
        String ans = scanner.nextLine().trim().toUpperCase();
        return "Y".equals(ans);
    }

    public Dice getDice(MovementStrategy movementStrategy) {
        System.out.println("Enter the number of dice: ");
        int numberOfDice = scanner.nextInt();

        System.out.println("Enter minValue on dice :");
        int minValue = scanner.nextInt();

        System.out.println("Enter maxValue :");
        int maxValue = scanner.nextInt();
        scanner.nextLine();

        if (numberOfDice <= 0 || minValue <= 0 || maxValue <= 0) {
            System.out.println("The number of dice must be positive. Please try again.");
            return getDice(movementStrategy);
        }

        return new Dice(minValue, maxValue, numberOfDice, movementStrategy);
    }

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

    public int getNumberOfLadders(int boardSize) {
        int numberOfLadders;
        System.out.println("Enter the number of ladders: ");
        while (true) {
            try {
                numberOfLadders = Integer.parseInt(scanner.nextLine());
                if (numberOfLadders < 0) {
                    System.out.println("The number of ladders cannot be negative. Please enter a positive integer or 0:");
                } else if (numberOfLadders >= boardSize) {
                    System.out.println("The number of snakes cannot be greater than the boardSize. Please retry");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer or 0:");
            }
        }
        return numberOfLadders;
    }
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

    public List<Snake> getSnakePositions(int numberOfSnakes) {
        List<Snake> snakeList = new ArrayList<>();
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
                        // Assuming Snake constructor takes head and tail as arguments
                        Snake snake = new Snake(head, tail);
                        snakeList.add(snake);
                        break; // Exit the loop after successful input
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter two integers separated by a space.");
                }
            }
        }
        return snakeList;
    }

    public List<Ladder> getLadderPositions(int numberOfLadders) {
        List<Ladder> ladderList = new ArrayList<>();
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
                        // Assuming Ladder constructor takes bottom and top as arguments
                        Ladder ladder = new Ladder(bottom, top);
                        ladderList.add(ladder);
                        break; // Exit the loop after successful input
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter two integers separated by a space.");
                }
            }
        }
        return ladderList;
    }


    public int addPlayers(Queue<Player> players, int configPlayer) {

        int numberOfPlayers = 0;
        if(configPlayer != -1) {
            numberOfPlayers = configPlayer;
        }
        else {
            System.out.print("Enter the number of players: ");
            numberOfPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter name and starting location for player " + (i + 1) + " (format: Name Location):");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts.length == 2) {
                try {
                    String name = parts[0];
                    int startingLocation = Integer.parseInt(parts[1]);
                    players.offer(new Player(name, startingLocation));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for starting location. Please enter a valid integer.");
                    i--;
                }
            } else {
                System.out.println("Invalid input format. Please try again.");
                i--;
            }
        }
        return numberOfPlayers;
    }


    public void close() {
        scanner.close();
    }
}
