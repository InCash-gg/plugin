package gg.incash.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import gg.incash.common.command.IncashPurchaseExecutor;
import gg.incash.common.config.Config;
import gg.incash.common.logger.IncashLogger;

public class VelocityPurchaseExecutor extends IncashPurchaseExecutor {

    private final ProxyServer proxy;

    public VelocityPurchaseExecutor(ProxyServer proxyServer, String jsonData, IncashLogger logger, Config config) {
        super(jsonData, logger, config);
        this.proxy = proxyServer;
    }

    @Override
    public boolean isPlayerOnline(final String playerName) {
        return this.proxy.getPlayer(playerName).isPresent();
    }

    @Override
    public void executeCommand(final String command) {
        this.proxy.getCommandManager().executeAsync(this.proxy.getConsoleCommandSource(), command);
    }

}
