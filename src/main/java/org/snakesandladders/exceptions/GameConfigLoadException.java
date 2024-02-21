package org.snakesandladders.exceptions;

public class GameConfigLoadException extends Exception {

    public GameConfigLoadException(String message) {
        super(message);
    }

    public GameConfigLoadException(String message, Throwable cause) {
        super(message, cause);
    }

}
