package net.bagatelle.afkpeace.util;

import java.net.Socket;

import net.bagatelle.afkpeace.constants.ReconnectionConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

public class ConnectUtil {

    private boolean autoReconnectActive = false;
    public int reconnectTimer = 0;

    public void connectToServer(ServerInfo serverAddress) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (serverAddress != null) {
            mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverAddress));
        } else {
            // TODO PUT CODE HERE TO HANDLE NOT HAVING A SERVER IP
        }
    }

    public void autoReconnectToServer(ServerInfo serverInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ReconnectTestThread reconnectTestThread = new ReconnectTestThread(serverInfo);
        reconnectTestThread.start();
        boolean canConnect = false;
        boolean reachedMaxAttempts = false;
        while (!canConnect || !reachedMaxAttempts) {
            canConnect = reconnectTestThread.getCanReconnect();
            reachedMaxAttempts = reconnectTestThread.getReachedMaxAttempts();
        }
        try {
            reconnectTestThread.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if(canConnect) {
            try {
                mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverInfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (reachedMaxAttempts) {
            System.out.println("Can't connect, reached max number of attempts");
            // TODO Make this show up ingame somehow
        }
    }

    public void setAutoReconnectActive(boolean setpoint) {
        autoReconnectActive = setpoint;
        System.out.println(autoReconnectActive);
    }

    public boolean getAutoReconnectActive() {
        return autoReconnectActive;
    }

}