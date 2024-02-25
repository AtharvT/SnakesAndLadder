package org.snakesandladders.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakesandladders.strategy.*;

import java.io.IOException;

public class MovementStrategyDeserializer extends JsonDeserializer<MovementStrategy> {
    private static final Logger logger = LoggerFactory.getLogger(MovementStrategyDeserializer.class);
    @Override
    public MovementStrategy deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String strategyType = p.getText().toUpperCase();
        switch (strategyType) {
            case "SUM":
                return new SumStrategy();
            case "MAX":
                return new MaxStrategy();
            case "MIN":
                return new MinStrategy();
            default:
                logger.error("Unknown movement strategy: {}", strategyType);
                throw new IOException("Unknown movement strategy: " + strategyType);
        }
    }
}

