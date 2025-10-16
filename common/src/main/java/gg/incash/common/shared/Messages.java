package gg.incash.common.shared;

public abstract class Messages<T> {


    public static final String PREFIX = "&a[Incash] ";

    public final T notEnoughRights = this.color(PREFIX + "&cYou don't have rights to perform this command");
    public final T wrongSyntax = this.color(PREFIX + "&4Wrong syntax. &rTry: /incash reload");
    public final T reloadSuccess = this.color(PREFIX + "&2Config reloaded successfully!");
    public final T reloadError = this.color(PREFIX + "&4Config contains errors!");

    protected abstract T color(final String message);

    public abstract T appendMessage(final T message, final String append);
}
