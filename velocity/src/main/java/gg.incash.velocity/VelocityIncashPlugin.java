package gg.incash.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import gg.incash.common.config.*;
import gg.incash.common.logger.IncashLogger;
import gg.incash.common.socket.SocketClient;
import ninja.leaping.configurate.ConfigurationNode;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;
import java.time.Duration;

@Plugin(
        id = "incash",
        name = "Incash",
        version = "1.0.0",
        description = "Incash integration for Velocity servers",
        url = "https://incash.gg/",
        authors = "Incash"
)
public class VelocityIncashPlugin {

    private final ProxyServer server;
    private final Logger velocityLogger;
    private final Path dir;
    private final PluginContainer pluginContainer;
    private SocketClient socketClient;

    @Inject
    public VelocityIncashPlugin(final ProxyServer server, final Logger velocityLogger, @DataDirectory final Path dataDirectory, final PluginContainer pluginContainer) {
        this.server = server;
        this.velocityLogger = velocityLogger;
        this.pluginContainer = pluginContainer;
        this.dir = dataDirectory;
    }

    @Subscribe
    public void onEnable(final ProxyInitializeEvent event) {
        final IncashLogger logger = new VelocityIncashLogger(this.velocityLogger);

        try {
            final ResourceLoader<ConfigurationNode> resourceLoader = new VelocityResourceLoader(this.getClass(), this.dir);
            final ConfigLoader configLoader = new VelocityConfigLoader(resourceLoader, "config.yml");
            final Config config = new Config(configLoader);
            final VelocityMessages messages = new VelocityMessages();

            socketClient = new SocketClient(config.getApiUrl(), config.getShopId(), config.getServerId(), SocketClient.ServerCore.VELOCITY, logger);
            socketClient.connect();

            this.server.getScheduler().buildTask(pluginContainer, new Runnable() {
                @Override
                public void run() {
                    try {
                        if (socketClient.isConnected()) {
                            if (config.getDebug()) {
                                logger.debug(String.format("Sending current online(%s) to API", server.getPlayerCount()));
                            }
                            JSONObject data = new JSONObject();
                            data.put("playersOnline", server.getPlayerCount());
                            socketClient.emit("online_update", data);
                        }
                    } catch (Exception e) {
                        logger.error("Failed to build JSON payload: " + e.getMessage());
                    }
                }
            }).repeat(Duration.ofSeconds(10)).schedule();

            socketClient.getSocket().on("purchase", args -> {
                if (args.length > 0)
                    server.getScheduler().buildTask(this, new VelocityPurchaseExecutor(server, args[0].toString(), logger, config)).schedule();
            });

            this.server.getCommandManager().register("incash", new VelocityIncashCommand(config, messages));
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Subscribe
    public void onDisable() {
        if (socketClient != null) socketClient.disconnect();
    }
}