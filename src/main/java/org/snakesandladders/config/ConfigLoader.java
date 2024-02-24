package org.snakesandladders.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakesandladders.exceptions.GameConfigLoadException;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    /**
     * Loads configuration from a JSON file into a specified class.
     *
     * @param configPath  Path to the configuration file.
     * @param configClass Class of the configuration to load.
     * @return An instance of the configuration class populated with values from the file.
     * @throws GameConfigLoadException If the configuration cannot be loaded.
     */
    public static <T> T loadConfig(String configPath, Class<T> configClass) throws GameConfigLoadException {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try (InputStream configFile = ConfigLoader.class.getResourceAsStream(configPath)) {
            if (configFile == null) {
                throw new GameConfigLoadException("Configuration file " + configPath + " is missing or not readable. Please ensure the file exists in the resources directory.");
            }
            T config = mapper.readValue(configFile, configClass);
            if (config instanceof Config) {
                ((Config) config).basicValidation();
            }
            return config;
        } catch (IOException e) {
            logger.error("Configuration file {} is missing or not readable. Please ensure the file exists in the resources directory.", configPath);
            throw new GameConfigLoadException("Failed to load configuration from " + configPath, e);
        }
    }
}
