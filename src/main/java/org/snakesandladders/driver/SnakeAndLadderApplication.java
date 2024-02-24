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
            System.out.println("Welcome to Snakes and Ladders! Enter number of games to play: ");
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
