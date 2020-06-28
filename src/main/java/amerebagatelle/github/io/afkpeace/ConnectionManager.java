package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.miscellaneous.ReconnectThread;
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

    // Handling the reconnect feature
    public void startReconnect(ServerInfo target) {
        this.minecraft.getNetworkHandler().getConnection().disconnect(new LiteralText("Reconnecting"));
        this.minecraft.disconnect();
        reconnectThread = new ReconnectThread(target);
        reconnectThread.start();
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceReconnection", new TranslatableText("reconnect.reason")));
    }

    public void finishReconnect() {
        connectToServer(AFKPeace.currentServerEntry);
    }

    public void cancelReconnect() {
        try {
            reconnectThread.join();
        } catch (InterruptedException ignored) {
        }
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceDisconnect", new LiteralText("Couldn't reconnect.")));
    }

    // Regular connecting utilities
    public void connectToServer(ServerInfo target) {
        this.minecraft.openScreen(new ConnectScreen(new MultiplayerScreen(new TitleScreen()), this.minecraft, target));
    }

    public void disconnectFromServer(Text reason) {
        isDisconnecting = true;
        this.minecraft.getNetworkHandler().getConnection().disconnect(reason);
        this.minecraft.disconnect();
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceDisconnect", reason));
    }
}
