package gg.incash.bungeecord;

import gg.incash.common.shared.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class BungeeCordMessages extends Messages<BaseComponent> {

    @Override
    protected BaseComponent color(final String message) {
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        return new TextComponent(TextComponent.fromLegacyText(coloredMessage));
    }

    @Override
    public BaseComponent appendMessage(final BaseComponent message, final String append) {
        BaseComponent finalMessage = message.duplicate();
        finalMessage.addExtra(append);
        return finalMessage;
    }

}
