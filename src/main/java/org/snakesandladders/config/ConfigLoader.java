package org.snakesandladders.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.snakesandladders.exceptions.GameConfigLoadException;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static GameConfig loadConfig(String filePath) throws GameConfigLoadException {
        File configFile = new File(filePath);

        if (!configFile.exists() || !configFile.canRead()) {
            throw new GameConfigLoadException("Configuration file is missing or not readable");
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(configFile, GameConfig.class);
        } catch (IOException e) {
            throw new GameConfigLoadException("Failed to load game configuration", e);
        }
    }
}
