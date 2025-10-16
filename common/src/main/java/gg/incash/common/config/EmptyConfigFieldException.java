package gg.incash.common.config;

public class EmptyConfigFieldException extends Exception {

    public EmptyConfigFieldException(final String fieldName) {
        super(String.format("Config field \"%s\" is empty", fieldName));
    }

}
