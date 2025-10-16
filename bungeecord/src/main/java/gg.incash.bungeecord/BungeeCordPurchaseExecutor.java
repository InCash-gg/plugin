package gg.incash.bungeecord;

import gg.incash.common.command.IncashPurchaseExecutor;
import gg.incash.common.config.Config;
import gg.incash.common.logger.IncashLogger;
import net.md_5.bungee.api.ProxyServer;

public class BungeeCordPurchaseExecutor extends IncashPurchaseExecutor {

    private final ProxyServer proxy;

    public BungeeCordPurchaseExecutor(ProxyServer proxyServer, String jsonData, IncashLogger logger, Config config) {
        super(jsonData, logger, config);
        this.proxy = proxyServer;
    }

    @Override
    public boolean isPlayerOnline(String playerName) {
        return this.proxy.getPlayer(playerName) != null;
    }

    @Override
    public void executeCommand(String command) {
        this.proxy.getPluginManager().dispatchCommand(this.proxy.getConsole(), command);
    }
}
