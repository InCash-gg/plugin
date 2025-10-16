package gg.incash.bungeecord;

import gg.incash.common.config.ResourceLoader;
import gg.incash.common.config.ResourceLoaderException;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.nio.file.Path;

public class BungeeCordResourceLoader extends ResourceLoader<Configuration> {

    public BungeeCordResourceLoader(final Class<?> loadingClass, final File dataFolder) {
        super(loadingClass, dataFolder.toPath());
    }

    @Override
    public Configuration loadResource(final Path resourcePath) throws ResourceLoaderException {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(resourcePath.toFile());
        } catch (final Exception exception) {
            throw new ResourceLoaderException(ResourceLoaderException.Reason.FILE_NOT_LOADED, resourcePath.getFileName().toString(), exception);
        }
    }

}
