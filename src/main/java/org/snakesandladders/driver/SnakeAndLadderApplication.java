package org.snakesandladders.driver;

import org.snakesandladders.engine.RulesEngine;
import org.snakesandladders.exceptions.GameConfigLoadException;
import org.snakesandladders.engine.GameEngine;
import org.snakesandladders.utils.InputHandler;

public class SnakeAndLadderApplication {

    public static void main(String[] args) throws GameConfigLoadException {

        InputHandler inputHandler = new InputHandler();
        boolean continueGame = false;

        do {
            System.out.println("WELCOME TO SNAKES AND LADDERS!");
            System.out.println("");
            System.out.println("");
            try {
                boolean isManualMode = inputHandler.isManualMode();
                RulesEngine rulesEngine = new RulesEngine();
                GameEngine gameEngine = new GameEngine(rulesEngine, inputHandler, isManualMode);
                gameEngine.playGame();
            } catch (Exception e) {
                System.err.println("Failed to load game configuration: " + e.getMessage());
            }
            continueGame = inputHandler.isContinueGame();
        } while(continueGame);

        System.out.println("FINISHING THE GAME. THANK YOU");
        inputHandler.close();
    }
}
