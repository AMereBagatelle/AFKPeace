package amerebagatelle.github.io.afkpeace.miscellaneous;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

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
        for (int i = 0; i <= SettingsManager.maxReconnectTries; i++) {
            try {
                Thread.sleep(SettingsManager.secondsBetweenReconnectionAttempts * 1000);
                Socket connectionAttempt = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                connectionAttempt.close();
                canReconnect = 1;
                break;
            } catch (UnknownHostException e) {
            } catch (IOException e) {
            } catch (InterruptedException e) {
            }
        }
        if(canReconnect != 1) {
            canReconnect = 2;
        }
        synchronized(this) {AFKPeace.stateVariables.canReconnect = canReconnect;}
    }

}