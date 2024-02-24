package org.snakesandladders.config;

public class DiceConfig implements Config {
    private int diceMinValue;
    private int diceMaxValue;

    public DiceConfig() {
    }

    public int getDiceMinValue() {
        return diceMinValue;
    }

    public void setDiceMinValue(int diceMinValue) {
        this.diceMinValue = diceMinValue;
    }

    public int getDiceMaxValue() {
        return diceMaxValue;
    }

    public void setDiceMaxValue(int diceMaxValue) {
        this.diceMaxValue = diceMaxValue;
    }

    @Override
    public void basicValidation() {
        if (diceMaxValue < diceMinValue) {
            throw new IllegalArgumentException("Min dice value must be smaller than max DiceValue");
        }
    }
}
