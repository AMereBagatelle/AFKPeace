package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.miscellaneous.ReconnectThread;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class ConnectionManager {
    public static ConnectionManager INSTANCE = new ConnectionManager();
    private final MinecraftClient minecraft;
    private ReconnectThread reconnectThread;

    private static final YggdrasilAuthenticationService yas = new YggdrasilAuthenticationService(MinecraftClient.getInstance().getNetworkProxy(), UUID.randomUUID().toString());
    private static final YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) yas.createUserAuthentication(Agent.MINECRAFT);

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
        this.minecraft.getNetworkHandler().getConnection().disconnect(new TranslatableText("reconnecting"));
        this.minecraft.disconnect();
        reconnectThread = new ReconnectThread(target);
        reconnectThread.start();
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceReconnection", new TranslatableText("reconnect.reason")));
    }

    /**
     * Called by the Reconnection thread to finish reconnecting to the server.
     */
    public void finishReconnect() {
        connectToServer(AFKPeace.currentServerEntry);
    }

    /**
     * Called by the Reconnection thread if it cannot connect to the target server.
     */
    public void cancelReconnect() {
        try {
            reconnectThread.join();
        } catch (InterruptedException ignored) {
        }
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceDisconnect", new LiteralText("Couldn't reconnect.")));
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
            this.minecraft.getNetworkHandler().getConnection().disconnect(reason);
            this.minecraft.disconnect();
            this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceDisconnect", reason));
        } else {
            if (AFKPeace.currentServerEntry != null) startReconnect(AFKPeace.currentServerEntry);
        }
    }

    public boolean checkSessionActive() {
        return yua.isLoggedIn() || FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
