package net.bagatelle.afkpeace.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.bagatelle.afkpeace.constants.ReconnectionConstants;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

public class ReconnectTestThread extends Thread {

    private boolean canReconnect;
    private boolean reachedMaxAttempts;

    private ServerInfo serverInfo;
    private ServerAddress serverAddress;

    public ReconnectTestThread(ServerInfo serverInfo) {
        super();
        this.canReconnect = false;
        this.reachedMaxAttempts = false;
        this.serverInfo = serverInfo;
        this.serverAddress = ServerAddress.parse(serverInfo.address);
    }

    public void run() {
        for (int i = 0; i <= ReconnectionConstants.maxReconnectTries; i++) {
            try {
                Thread.sleep(1000);
                Socket connectionAttempt = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                synchronized(this) {canReconnect = true;}
                break;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Attempt: " + i);
        }
        if(!canReconnect) {
            reachedMaxAttempts = true;
        }
        return;
    }

    public boolean getCanReconnect() {
        synchronized(this) {return canReconnect;}
    }

    public boolean getReachedMaxAttempts() {
        synchronized(this) {return reachedMaxAttempts;}
    }

}