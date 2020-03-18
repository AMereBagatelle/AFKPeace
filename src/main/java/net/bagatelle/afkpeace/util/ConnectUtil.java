package net.bagatelle.afkpeace.util;

import java.net.Socket;

import net.bagatelle.afkpeace.constants.ReconnectionConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ServerAddress;

public class ConnectUtil {

    private boolean autoReconnectActive = false;
    
    public void connectToServer(ServerInfo serverAddress) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(serverAddress != null) {
            mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverAddress));
        } else {
            // TODO PUT CODE HERE TO HANDLE NOT HAVING A SERVER IP
        }
    }

    public void autoReconnectToServer(ServerInfo serverInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ServerAddress serverAddress = ServerAddress.parse(serverInfo.address);
        for(int attempt=0; attempt>ReconnectionConstants.maxReconnectTries; attempt++) {
            try {
                Socket connectionAttempt = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverInfo));
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
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