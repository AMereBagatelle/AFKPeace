package amerebagatelle.github.io.afkpeace.client.miscellaneous;

import amerebagatelle.github.io.afkpeace.client.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.client.ConnectionManager;
import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

import java.io.IOException;
import java.net.Socket;

@Environment(EnvType.CLIENT)
public class ReconnectThread extends Thread {
    private final int timesToAttempt = Integer.parseInt(SettingsManager.loadSetting("reconnectAttemptNumber"));
    private final int secondsBetweenAttempts = Integer.parseInt(SettingsManager.loadSetting("secondsBetweenReconnectAttempts"));

    private final ServerAddress serverAddress;

    public ReconnectThread(ServerInfo serverInfo) {
        super();
        this.serverAddress = ServerAddress.parse(serverInfo.address);
    }

    /**
     * Tries to connect to the server using a socket as many times as is set, and returns if it could connect
     */
    @Override
    public void run() {
        for (int i = 0; i < timesToAttempt; i++) {
            Socket connectionAttempt;
            try {
                Thread.sleep(secondsBetweenAttempts * 1000);
                connectionAttempt = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                connectionAttempt.close();
                synchronized (this) {
                    AFKPeaceClient.LOGGER.info("Reconnecting to server.");
                    ConnectionManager.INSTANCE.isReconnecting = true;
                }
                break;
            } catch (IOException | InterruptedException e) {
                AFKPeaceClient.LOGGER.info("Attempt failed.  Reason: " + e.getMessage() + " Attempt #: " + i + 1);
            }
        }
        if (!ConnectionManager.INSTANCE.isReconnecting) {
            MinecraftClient.getInstance().execute(() -> ConnectionManager.INSTANCE.cancelReconnect());
        }
    }
}