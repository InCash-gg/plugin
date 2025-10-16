package gg.incash.bukkit;

import gg.incash.common.command.IncashCommand;
import gg.incash.common.config.Config;
import gg.incash.common.shared.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BukkitCommand implements CommandExecutor, IncashCommand<CommandSender, String> {
    private final Config config;
    private final Messages<String> messages;

    public BukkitCommand(final Config config, Messages<String> messages) {
        this.config = config;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        this.executeCommand(sender, args);
        return true;
    }

    @Override
    public void sendMessage(final CommandSender sender, final String message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(final CommandSender sender, final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Messages<String> getMessages() {
        return this.messages;
    }
}
