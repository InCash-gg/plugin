package gg.incash.bukkit;

import gg.incash.common.shared.Messages;
import org.bukkit.ChatColor;

public class BukkitMessages extends Messages<String> {

    @Override
    protected String color(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String appendMessage(final String message, final String append) {
        return message + append;
    }

}