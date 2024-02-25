package org.snakesandladders.config;

import org.junit.jupiter.api.Test;
import org.snakesandladders.exceptions.GameConfigLoadException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void testLoadValidConfig() throws Exception {
        String configPath = "/validConfig.json";
        GameConfig result = ConfigLoader.loadConfig(configPath, GameConfig.class);
        assertNotNull(result);
    }

    @Test
    void testConfigFileNotFound() {
        String configPath = "/nonexistentConfig.json";
        assertThrows(GameConfigLoadException.class, () -> ConfigLoader.loadConfig(configPath, GameConfig.class));
    }

    @Test
    void testInvalidConfigFormat() {
        String configPath = "/invalidConfig.json";
        assertThrows(GameConfigLoadException.class, () -> ConfigLoader.loadConfig(configPath, GameConfig.class));
    }

    @Test
    void testEmptyConfigFile() {
        String configPath = "/emptyConfig.json"; // An empty JSON file
        assertThrows(GameConfigLoadException.class, () -> ConfigLoader.loadConfig(configPath, GameConfig.class));
    }

    @Test
    void testConfigWithExtraFields() throws Exception {
        String configPath = "/extraFieldsConfig.json"; // Valid JSON with fields not defined in the class
        assertThrows(GameConfigLoadException.class, () -> ConfigLoader.loadConfig(configPath, GameConfig.class));
    }


    @Test
    void testResourceStreamClosed() throws Exception {
        String configPath = "/validConfig.json";
        GameConfig result = ConfigLoader.loadConfig(configPath, GameConfig.class);
        assertNotNull(result);
    }


}