package gg.incash.bungeecord;

import gg.incash.common.command.IncashCommand;
import gg.incash.common.config.Config;
import gg.incash.common.shared.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class BungeeCordCommand extends Command implements IncashCommand<CommandSender, BaseComponent>, TabExecutor {

    private final Config config;
    private final Messages<BaseComponent> messages;

    public BungeeCordCommand(final Config config, final Messages<BaseComponent> messages) {
        super("incash");
        this.config = config;
        this.messages = messages;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        this.executeCommand(sender, args);
    }

    @Override
    public void sendMessage(final CommandSender sender, final BaseComponent message) {
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
    public Messages<BaseComponent> getMessages() {
        return this.messages;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (sender.hasPermission("op") && args[0].equalsIgnoreCase("incash")) {
            List<String> commandList = new ArrayList<>();
            commandList.add("reload");
            return commandList;
        }
        return null;
    }

}
