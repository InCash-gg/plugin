package gg.incash.velocity;

import gg.incash.common.logger.IncashLogger;
import org.slf4j.Logger;

public class VelocityIncashLogger implements IncashLogger {

    private final Logger logger;

    public VelocityIncashLogger(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }
}
