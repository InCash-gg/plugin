package gg.incash.common.logger;

public interface IncashLogger {
    void info(final String message);

    void error(final String message);

    default void debug(final String message) {
        this.info(String.format("[DEBUG] %s", message));
    }
}
