package gg.incash.common.socket;

import gg.incash.common.logger.IncashLogger;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;

import java.net.URI;

public class SocketClient {

    public enum ServerCore {
        BUKKIT,
        BUNGEECORD,
        VELOCITY
    }

    private Socket socket;
    private final String uri;
    private final String shopId;
    private final String serverId;
    private final ServerCore core;
    private final IncashLogger logger;
    private Boolean connected;

    public SocketClient(String uri, String shopId, String serverId, ServerCore core, IncashLogger logger) {
        this.uri = uri;
        this.shopId = shopId;
        this.serverId = serverId;
        this.core = core;
        this.logger = logger;
    }

    public Boolean isConnected() {
        return connected;
    }

    public Socket getSocket() {
        return socket;
    }

    public void connect() {
        try {
            IO.Options options = new IO.Options();
            options.query = String.format("shop_id=%s&server_id=%s&core=%s", this.shopId, this.serverId, this.core);
            options.reconnection = true;
            options.transports = new String[]{WebSocket.NAME};
            options.reconnectionAttempts = 100;
            options.reconnectionDelay = 10000;

            socket = IO.socket(URI.create(uri), options);

            socket.on(Socket.EVENT_CONNECT, args -> {
                        this.connected = true;
                        logger.info(String.format("Connected to Incash API. Shop ID: %s, Server ID: %s", this.shopId, this.serverId));
                    }
            );

            socket.on("wrong_config", args -> {
                logger.error("Disconnected from Incash API. Make sure that you have specified the correct server id for this server.");
            });

            socket.on(Socket.EVENT_DISCONNECT, args -> {
                this.connected = false;
                logger.info("Disconnected from Incash API");
            });

            socket.on(Socket.EVENT_CONNECT_ERROR, args ->
                    logger.error("Incash connection error:" + args[0])
            );

            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void emit(String event, Object data) {
        if (socket != null && socket.connected()) {
            socket.emit(event, data);
        }
    }

    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
            socket.close();
        }
    }
}
