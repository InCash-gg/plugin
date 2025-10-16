package gg.incash.common.command;

import gg.incash.common.config.Config;
import gg.incash.common.config.EmptyConfigFieldException;
import gg.incash.common.config.ResourceLoaderException;
import gg.incash.common.shared.Messages;

import java.util.*;

public interface IncashCommand<S, M> {

    Set<String> RELOAD_ARGS = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("reload")));

    default void executeCommand(final S sender, final String[] args) {
        if (!this.hasPermission(sender, "incash.reload")) {
            this.sendMessage(sender, this.getMessages().notEnoughRights);
            return;
        }

        if (args.length != 1 || !RELOAD_ARGS.contains(args[0].toLowerCase(Locale.ROOT))) {
            this.sendMessage(sender, this.getMessages().wrongSyntax);
            return;
        }

        try {
            this.getConfig().loadValues();
            this.sendMessage(sender, this.getMessages().reloadSuccess);
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            M message = this.getMessages().appendMessage(this.getMessages().reloadError, exception.getMessage());
            this.sendMessage(sender, message);
        }
    }

    void sendMessage(final S sender, final M message);

    boolean hasPermission(final S sender, final String permission);

    Config getConfig();

    Messages<M> getMessages();
}
