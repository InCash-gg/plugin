package gg.incash.bukkit;

import gg.incash.common.config.Config;
import gg.incash.common.config.ConfigLoader;
import gg.incash.common.config.EmptyConfigFieldException;
import gg.incash.common.config.ResourceLoaderException;
import gg.incash.common.logger.IncashLogger;
import gg.incash.common.shared.Messages;
import gg.incash.common.socket.SocketClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

public final class BukkitIncashPlugin extends JavaPlugin {
    private SocketClient socketClient;

    @Override
    public void onEnable() {
        final IncashLogger logger = new BukkitLogger(this.getLogger());

        try {
            final ConfigLoader configLoader = new BukkitConfigLoader(this);
            final Config config = new Config(configLoader);
            final Messages<String> messages = new BukkitMessages();

            socketClient = new SocketClient(config.getApiUrl(), config.getShopId(), config.getServerId(), SocketClient.ServerCore.BUKKIT, logger);
            socketClient.connect();

            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        if (socketClient.isConnected()) {
                            if (config.getDebug()) {
                                logger.debug(String.format("Sending current online(%s) to API", getServer().getOnlinePlayers().size()));
                            }
                            JSONObject data = new JSONObject();
                            data.put("playersOnline", getServer().getOnlinePlayers().size());
                            socketClient.emit("online_update", data);
                        }
                    } catch (Exception e) {
                        logger.error("Failed to build JSON payload: " + e.getMessage());
                    }
                }
            }, 0L, 200L); // 10 seconds

            socketClient.getSocket().on("purchase", args -> {
                if(args.length > 0) Bukkit.getScheduler().runTask(this, new BukkitPurchaseExecutor(args[0].toString(), logger, config));
            });

            getCommand("incash").setExecutor(new BukkitCommand(config, messages));
            getCommand("incash").setTabCompleter(new BukkitTabCompleter());
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            logger.error(exception.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        if (socketClient != null) socketClient.disconnect();
    }

}
