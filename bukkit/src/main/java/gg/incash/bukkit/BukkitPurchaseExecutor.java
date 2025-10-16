package gg.incash.bukkit;

import gg.incash.common.command.IncashPurchaseExecutor;
import gg.incash.common.config.Config;
import gg.incash.common.logger.IncashLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPurchaseExecutor extends IncashPurchaseExecutor {

    public BukkitPurchaseExecutor(String jsonData, IncashLogger logger, Config config) {
        super(jsonData, logger, config);
    }

    @Override
    public boolean isPlayerOnline(String playerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void executeCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

}
