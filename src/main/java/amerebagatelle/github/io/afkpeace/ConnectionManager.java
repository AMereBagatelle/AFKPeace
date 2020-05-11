package amerebagatelle.github.io.afkpeace;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

@Environment(EnvType.CLIENT)
public class ConnectionManager {
    private final MinecraftClient minecraft;

    public ConnectionManager(MinecraftClient minecraft) {
        this.minecraft = minecraft;
    }

    public void connectToServer(ServerInfo target) {
        this.minecraft.openScreen(new ConnectScreen(new MultiplayerScreen(new TitleScreen()), this.minecraft, target));
    }

    public void disconnectFromServer() {

    }
}
