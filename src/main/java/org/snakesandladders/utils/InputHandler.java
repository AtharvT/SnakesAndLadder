package org.snakesandladders.utils;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
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

    public boolean loadFromConfig() {
        System.out.println("Do you want to set the snake and ladder position from config? (Y/N)");
        String ans = scanner.nextLine().trim().toUpperCase();
        return "Y".equals(ans);
    }

    public boolean askForManualRoll() {
        System.out.println("Would you like to roll the dice manually? (Y/N)");
        String ans = scanner.nextLine();
        return "Y".equals(ans);
    }

    public void close() {
        scanner.close();
    }
}
