package gg.incash.bukkit;

import gg.incash.common.logger.IncashLogger;

import java.util.logging.Logger;

public class BukkitLogger implements IncashLogger {

    private final Logger logger;

    public BukkitLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message) {
        logger.severe(message);
    }
}
