package gg.incash.bungeecord;

import gg.incash.common.config.ConfigLoader;
import gg.incash.common.config.ResourceLoader;
import gg.incash.common.config.ResourceLoaderException;
import net.md_5.bungee.config.Configuration;

public class BungeeCordConfigLoader implements ConfigLoader {

    private final ResourceLoader<Configuration> resourceLoader;
    private Configuration configFile;

    public BungeeCordConfigLoader(final ResourceLoader<Configuration> resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void reloadConfig() throws ResourceLoaderException {
        this.resourceLoader.saveDefault("config.yml");
        this.configFile = this.resourceLoader.load("config.yml");
    }

    @Override
    public boolean getBoolean(final String key) {
        return this.configFile.getBoolean(key);
    }

    @Override
    public String getString(final String key) {
        return this.configFile.getString(key);
    }


}
