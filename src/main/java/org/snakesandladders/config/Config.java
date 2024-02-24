package org.snakesandladders.config;

public interface Config {
    /**
     * Performs basic validation on the configuration parameters.
     * Throws IllegalArgumentException if validation fails.
     * Have added this to ensure that a basic validation is maintained at the time of loading the config
     */
    void basicValidation();
}
