package amerebagatelle.github.io.afkpeace.miscellaneous;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

@Environment(EnvType.CLIENT)
public class ReconnectTestThread extends Thread {

    private int canReconnect;

    private ServerAddress serverAddress;

    public ReconnectTestThread(ServerInfo serverInfo) {
        super();
        this.canReconnect = 0;
        this.serverAddress = ServerAddress.parse(serverInfo.address);
    }

    // Tries to connect to the server using a socket as many times as is set, and returns if it could connect
    public void run() {

    }

}