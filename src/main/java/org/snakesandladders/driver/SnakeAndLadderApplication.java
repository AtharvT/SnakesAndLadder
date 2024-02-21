package org.snakesandladders.driver;

import org.snakesandladders.config.ConfigLoader;
import org.snakesandladders.config.GameConfig;
import org.snakesandladders.exceptions.GameConfigLoadException;
import org.snakesandladders.engine.GameEngine;
import org.snakesandladders.model.Player;

import java.util.Scanner;

public class SnakeAndLadderApplication {

    public static void main(String[] args) throws GameConfigLoadException {

        Scanner scanner = new Scanner(System.in);
        String configPath = "src/main/java/org/snakesandladders/config/config.json";
        GameConfig config = ConfigLoader.loadConfig(configPath);
        System.out.println("Enter number of test cases");
        int t = scanner.nextInt();
        scanner.nextLine();

        while (t-- > 0) {

            GameEngine gameEngine = new GameEngine(config.getBoardSize(), config.getNumberOfDies(), config);
            for (int i = 0; i < config.getNumberOfPlayers(); i++) {
                System.out.println("Enter player name");
                String pName = scanner.nextLine();
                Player player = new Player(pName, 1);
                gameEngine.addPlayer(player);
            }
            gameEngine.playGame();
        }
    }

}
