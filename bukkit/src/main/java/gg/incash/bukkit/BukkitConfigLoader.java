package gg.incash.bukkit;

import gg.incash.common.config.ConfigLoader;
import org.bukkit.plugin.Plugin;

public class BukkitConfigLoader implements ConfigLoader {

    private final Plugin plugin;

    public BukkitConfigLoader(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reloadConfig() {
        this.plugin.saveDefaultConfig();
        this.plugin.reloadConfig();
    }

    @Override
    public boolean getBoolean(final String key) {
        return this.plugin.getConfig().getBoolean(key);
    }

    @Override
    public String getString(final String key) {
        return this.plugin.getConfig().getString(key);
    }

}