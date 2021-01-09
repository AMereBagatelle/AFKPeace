package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.client.miscellaneous.ReconnectThread;
import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
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
    public boolean isReconnecting = false;

    public ConnectionManager() {
        this.minecraft = MinecraftClient.getInstance();
    }

    /**
     * Attempts to reconnect the client to the target server.
     *
     * @param target The server to connect to.
     */
    // Handling the reconnect feature
    public void startReconnect(ServerInfo target) {
        assert minecraft.getNetworkHandler() != null;
        this.minecraft.getNetworkHandler().getConnection().disconnect(new TranslatableText("reconnecting"));
        this.minecraft.disconnect();
        reconnectThread = new ReconnectThread(target);
        reconnectThread.start();
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), new LiteralText("AFKPeaceReconnection"), new TranslatableText("afkpeace.reconnect.reason")));
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
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), new LiteralText("AFKPeaceDisconnect"), new TranslatableText("afkpeace.reconnectfail")));
    }

    /**
     * Connects the client to the target server.
     *
     * @param target Server to connect to.
     */
    public void connectToServer(ServerInfo target) {
        this.minecraft.openScreen(new ConnectScreen(new MultiplayerScreen(new TitleScreen()), this.minecraft, target));
    }

    /**
     * Disconnects the client from the current server.
     *
     * @param reason Why the client disconnected.
     */
    public void disconnectFromServer(Text reason) {
        if (!Boolean.parseBoolean(SettingsManager.loadSetting("reconnectOnDamageLogout"))) {
            isDisconnecting = true;
            Objects.requireNonNull(this.minecraft.getNetworkHandler()).getConnection().disconnect(reason);
            this.minecraft.disconnect();
            this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), new LiteralText("AFKPeaceDisconnect"), reason));
        } else {
            if (AFKPeaceClient.currentServerEntry != null) startReconnect(AFKPeaceClient.currentServerEntry);
        }
    }
}
