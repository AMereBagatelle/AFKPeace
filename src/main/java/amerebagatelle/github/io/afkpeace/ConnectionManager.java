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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class ConnectionManager {
    private final MinecraftClient minecraft;

    public ConnectionManager(MinecraftClient minecraft) {
        this.minecraft = minecraft;
    }

    // Handling the reconnect feature
    public void startReconnect(ServerInfo target) {
        ReconnectThread thread = new ReconnectThread(target);
        thread.start();
    }

    public void finishReconnect() {
        connectToServer(AFKPeace.currentServerEntry);
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceReconnection", new TranslatableText("afkpeace.reconnect.reason"))); // TODO translation key
    }

    // Regular connecting utilities
    public void connectToServer(ServerInfo target) {
        this.minecraft.openScreen(new ConnectScreen(new MultiplayerScreen(new TitleScreen()), this.minecraft, target));
    }

    public void disconnectFromServer(Text reason) {
        this.minecraft.getNetworkHandler().getConnection().disconnect(reason);
        this.minecraft.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AFKPeaceDisconnect", reason));
    }
}
