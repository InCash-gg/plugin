package gg.incash.common.config;

public class ResourceLoaderException extends Exception  {
    private final Reason reason;

    public ResourceLoaderException(final Reason reason, final String fileName, final Throwable cause) {
        super(reason.getMessage(fileName), cause);
        this.reason = reason;
    }

    public Reason getReason() {
        return this.reason;
    }

    public enum Reason {
        DIRECTORY_NOT_CREATED("Plugin folder doesn't exists"),
        DEFAULT_FILE_NOT_SAVED("An error occurred while saving config file %s"),
        FILE_NOT_LOADED("Config file not found %s");

        private final String message;

        Reason(final String message) {
            this.message = message;
        }

        public String getMessage(final String fileName) {
            return String.format(this.message, fileName);
        }
    }
}
