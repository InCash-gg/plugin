package gg.incash.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import gg.incash.common.command.IncashCommand;
import gg.incash.common.config.Config;
import gg.incash.common.shared.Messages;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class VelocityIncashCommand implements SimpleCommand, IncashCommand<CommandSource, Component> {

    private final Config config;
    private final Messages<Component> messages;

    public VelocityIncashCommand(final Config config, final Messages<Component> messages) {
        this.config = config;
        this.messages = messages;
    }

    @Override
    public void execute(final Invocation invocation) {
        this.executeCommand(invocation.source(), invocation.arguments());
    }

    @Override
    public void sendMessage(final CommandSource sender, final Component message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(final CommandSource sender, final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        String[] args = invocation.arguments();

        if (args.length == 1) {
            if (invocation.source().hasPermission("incash.reload")) {
                suggestions.add("reload");
            }
        }

        return suggestions;
    }


    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Messages<Component> getMessages() {
        return this.messages;
    }

}
