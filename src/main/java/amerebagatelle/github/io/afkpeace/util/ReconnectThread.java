package amerebagatelle.github.io.afkpeace.util;

import amerebagatelle.github.io.afkpeace.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.ConnectionManager;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

import java.io.IOException;
import java.net.Socket;

@Environment(EnvType.CLIENT)
public class ReconnectThread extends Thread {
    private final int timesToAttempt = SettingsManager.settings.reconnectAttemptNumber;
    private final int secondsBetweenAttempts = SettingsManager.settings.secondsBetweenReconnectAttempts;

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
            try {
                Thread.sleep(secondsBetweenAttempts * 1000L);
                for (int i1 = 0; i1 < 10; i1++) {
                    pingServer();
                }
                synchronized (this) {
                    AFKPeaceClient.LOGGER.info("Reconnecting to server.");
                    MinecraftClient.getInstance().execute(() -> ConnectionManager.INSTANCE.finishReconnect());
                }
                return;
            } catch (IOException | InterruptedException e) {
                AFKPeaceClient.LOGGER.debug("Attempt failed.  Reason: " + e.getMessage() + " Attempt #: " + i + 1);
            }
        }
        MinecraftClient.getInstance().execute(() -> ConnectionManager.INSTANCE.cancelReconnect());
    }

    private void pingServer() throws IOException, InterruptedException {
        long startTime = System.nanoTime();
        Socket connectionSocket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
        connectionSocket.close();
        long endTime = System.nanoTime();
        if (endTime - startTime > 2 * 1e+9)
            throw new IOException("Ping was greater than five seconds, being " + (endTime - startTime) * 1e-9);
    }
}