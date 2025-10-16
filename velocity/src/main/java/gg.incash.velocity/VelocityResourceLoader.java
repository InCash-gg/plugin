package gg.incash.velocity;

import gg.incash.common.config.ResourceLoader;
import gg.incash.common.config.ResourceLoaderException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.nio.file.Path;

public class VelocityResourceLoader extends ResourceLoader<ConfigurationNode> {

    public VelocityResourceLoader(final Class<?> loadingClass, final Path dataDirectory) {
        super(loadingClass, dataDirectory);
    }

    @Override
    protected ConfigurationNode loadResource(final Path resourcePath) throws ResourceLoaderException {
        try {
            return YAMLConfigurationLoader.builder().setPath(resourcePath).build().load();
        } catch (final Exception exception) {
            throw new ResourceLoaderException(ResourceLoaderException.Reason.FILE_NOT_LOADED, resourcePath.getFileName().toString(), exception);
        }
    }

}
