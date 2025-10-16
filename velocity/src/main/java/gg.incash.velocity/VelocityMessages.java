package gg.incash.velocity;

import gg.incash.common.shared.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class VelocityMessages extends Messages<Component> {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    @Override
    protected Component color(final String message) {
        return LEGACY.deserialize(message);
    }

    @Override
    public Component appendMessage(final Component message, final String append) {
        return message.append(this.color(append));
    }

}