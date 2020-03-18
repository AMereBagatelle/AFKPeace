package net.bagatelle.afkpeace.util;

import net.bagatelle.afkpeace.constants.ReconnectionConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ServerInfo;

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

    public void setAutoReconnectActive(boolean setpoint) {
        autoReconnectActive = setpoint;
    }

    public boolean getAutoReconnectActive() {
        return autoReconnectActive;
    }

}