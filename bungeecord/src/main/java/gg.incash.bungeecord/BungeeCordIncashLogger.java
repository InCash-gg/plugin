package gg.incash.bungeecord;

import gg.incash.common.logger.IncashLogger;

import java.util.logging.Logger;

public class BungeeCordIncashLogger implements IncashLogger {

    private final Logger logger;

    public BungeeCordIncashLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(final String message) {
        this.logger.info(message);
    }

    @Override
    public void error(final String message) {
        this.logger.severe(message);
    }

}
