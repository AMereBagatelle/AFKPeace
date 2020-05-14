package amerebagatelle.github.io.afkpeace.miscellaneous;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

import java.io.IOException;
import java.net.Socket;

@Environment(EnvType.CLIENT)
public class ReconnectThread extends Thread {

    private final boolean canReconnect;
    private final int timesToAttempt = Integer.parseInt(SettingsManager.loadSetting("reconnectAttemptNumber"));
    private final int secondsBetweenAttempts = Integer.parseInt(SettingsManager.loadSetting("secondsBetweenReconnectAttempts"));

    private final ServerAddress serverAddress;

    public ReconnectThread(ServerInfo serverInfo) {
        super();
        canReconnect = false;
        this.serverAddress = ServerAddress.parse(serverInfo.address);
        System.out.println(this.serverAddress);
    }

    // Tries to connect to the server using a socket as many times as is set, and returns if it could connect
    public void run() {
        for (int i = 0; i < timesToAttempt; i++) {
            Socket connectionAttempt = null;
            try {
                connectionAttempt = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                connectionAttempt.close();
                synchronized (this) {
                    AFKPeace.isReconnecting = true;
                }
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(secondsBetweenAttempts);
                } catch (InterruptedException ignored) {
                }
            }

        }
    }

}