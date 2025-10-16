package gg.incash.common.config;

public interface ConfigLoader {

    void reloadConfig() throws ResourceLoaderException;

    boolean getBoolean(final String key);

    String getString(final String key);

}
