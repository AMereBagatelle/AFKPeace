package amerebagatelle.github.io.afkpeace;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ServerAddress;

public class ConnectionManager {
    private final MinecraftClient minecraft;

    public ConnectionManager(MinecraftClient minecraft) {
        this.minecraft = minecraft;
    }

    public void connectToServer(ServerAddress target) {
    }
}
