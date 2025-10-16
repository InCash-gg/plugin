package gg.incash.bungeecord;

import gg.incash.common.config.Config;
import gg.incash.common.config.ConfigLoader;
import gg.incash.common.config.EmptyConfigFieldException;
import gg.incash.common.config.ResourceLoaderException;
import gg.incash.common.logger.IncashLogger;
import gg.incash.common.shared.Messages;
import gg.incash.common.socket.SocketClient;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Plugin;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class BungeeCordIncashPlugin extends Plugin {

    private SocketClient socketClient;

    @Override
    public void onEnable() {
        final IncashLogger logger = new BungeeCordIncashLogger(this.getLogger());

        try {
            final BungeeCordResourceLoader resourceLoader = new BungeeCordResourceLoader(this.getClass(), this.getDataFolder());
            final ConfigLoader configLoader = new BungeeCordConfigLoader(resourceLoader);
            final Config config = new Config(configLoader);
            final Messages<BaseComponent> messages = new BungeeCordMessages();

            socketClient = new SocketClient(config.getApiUrl(), config.getShopId(), config.getServerId(), SocketClient.ServerCore.BUNGEECORD, logger);
            socketClient.connect();

            this.getProxy().getScheduler().schedule(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        if (socketClient.isConnected()) {
                            if (config.getDebug()) {
                                logger.debug(String.format("Sending current online(%s) to API", getProxy().getPlayers().size()));
                            }
                            JSONObject data = new JSONObject();
                            data.put("playersOnline", getProxy().getPlayers().size());
                            socketClient.emit("online_update", data);
                        }
                    } catch (Exception e) {
                        logger.error("Failed to build JSON payload: " + e.getMessage());
                    }
                }
            }, 0L, 10L, TimeUnit.SECONDS);

            socketClient.getSocket().on("purchase", args -> {
                if (args.length > 0)
                    this.getProxy().getScheduler().schedule(this, new BungeeCordPurchaseExecutor(this.getProxy(), args[0].toString(), logger, config), 0L, TimeUnit.SECONDS);
            });

            this.getProxy().getPluginManager().registerCommand(this, new BungeeCordCommand(config, messages));
        } catch (final ResourceLoaderException | EmptyConfigFieldException e) {
            logger.error(e.getMessage());
            ProxyServer.getInstance().getScheduler().cancel(this);
        }
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getScheduler().cancel(this);
        if (socketClient != null) socketClient.disconnect();
    }

}