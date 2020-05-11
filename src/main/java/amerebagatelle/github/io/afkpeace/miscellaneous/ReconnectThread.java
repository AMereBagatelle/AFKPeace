package amerebagatelle.github.io.afkpeace.miscellaneous;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

@Environment(EnvType.CLIENT)
public class ReconnectThread extends Thread {

    private final int canReconnect;

    private final ServerAddress serverAddress;

    public ReconnectThread(ServerInfo serverInfo) {
        super();
        this.canReconnect = 0;
        this.serverAddress = ServerAddress.parse(serverInfo.address);
    }

    // Tries to connect to the server using a socket as many times as is set, and returns if it could connect
    public void run() {

    }

}