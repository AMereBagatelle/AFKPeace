package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.util.ReconnectThread;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ConnectionManager {
    public static ConnectionManager INSTANCE = new ConnectionManager();
    private final MinecraftClient minecraft;
    private ReconnectThread reconnectThread;
    public boolean isDisconnecting = false;

    public ConnectionManager() {
        this.minecraft = MinecraftClient.getInstance();
    }

    /**
     * Attempts to reconnect the client to the target server.
     *
     * @param target The server to connect to.
     */
    public void startReconnect(ServerInfo target) {
        Objects.requireNonNull(this.minecraft.getNetworkHandler()).getConnection().disconnect(new TranslatableText("reconnecting"));
        this.minecraft.disconnect();
        reconnectThread = new ReconnectThread(target);
        reconnectThread.start();
        this.minecraft.setScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), new LiteralText("AFKPeaceReconnection"), new TranslatableText("afkpeace.reconnect.reason")));
    }

    /**
     * Called by the Reconnection thread to finish reconnecting to the server.
     */
    public void finishReconnect() {
        connectToServer(AFKPeaceClient.currentServerEntry);
    }

    /**
     * Called by the Reconnection thread if it cannot connect to the target server.
     */
    public void cancelReconnect() {
        try {
            reconnectThread.join();
        } catch (InterruptedException ignored) {
        }
        AFKPeaceClient.LOGGER.debug("Reconnecting cancelled.");
        this.minecraft.setScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), new LiteralText("AFKPeaceDisconnect"), new TranslatableText("afkpeace.reconnectfail")));
    }

    /**
     * Connects the client to the target server.
     *
     * @param targetInfo Server to connect to.
     */
    public void connectToServer(ServerInfo targetInfo) {
        ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), this.minecraft, ServerAddress.parse(targetInfo.address), targetInfo);
    }

    /**
     * Disconnects the client from the current server.
     *
     * @param reason Why the client disconnected.
     */
    public void disconnectFromServer(Text reason) {
        if (!AFKPeaceClient.CONFIG.reconnectOnDamageLogout) {
            isDisconnecting = true;
            Objects.requireNonNull(this.minecraft.getNetworkHandler()).getConnection().disconnect(reason);
            this.minecraft.disconnect();
            this.minecraft.setScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), new LiteralText("AFKPeaceDisconnect"), reason));
        } else {
            if (AFKPeaceClient.currentServerEntry != null) startReconnect(AFKPeaceClient.currentServerEntry);
        }
    }
}
