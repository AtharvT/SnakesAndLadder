package org.snakesandladders.model;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private final int minValue;
    private final int maxValue;
    private final int numberOfDice;

    public Dice(int minValue, int maxValue, int numberOfDice) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.numberOfDice = numberOfDice;
    }

    public int roll() {

        int totalSum = 0;
        int diceUsed = 0;
        while (numberOfDice > diceUsed) {
            totalSum += ThreadLocalRandom.current().nextInt(minValue, maxValue + 1);
            diceUsed++;
        }

        return totalSum;
    }

    public int manualRoll(int [] manualValues) {

        if(manualValues.length > numberOfDice) {
            throw new RuntimeException("Can't have more than + " + numberOfDice + " dice");
        }
        int totalSum = 0;
        for (int value : manualValues) {
            totalSum += value;
        }
        return totalSum;
    }
}
