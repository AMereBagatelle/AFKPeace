package net.bagatelle.afkpeace.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.TranslatableText;

public class ConnectUtil {

    public int reconnectTimer = 0;

    public void connectToServer(ServerInfo serverAddress) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (serverAddress != null) {
            mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverAddress));
        } else {
            mc.openScreen(new MultiplayerScreen(new TitleScreen()));
        }
    }

    public void autoReconnectToServer(ServerInfo serverInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ReconnectTestThread reconnectTestThread = new ReconnectTestThread(serverInfo);
        reconnectTestThread.start();
        int canConnect = 0;
        while (canConnect == 0) {
            canConnect = reconnectTestThread.getCanReconnect();
        }
        try {
            reconnectTestThread.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if(canConnect == 1) {
            mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverInfo));
        } else if (canConnect == 2) {
            System.out.println("Can't connect, reached max number of attempts!");
            mc.openScreen(new DisconnectRetryScreen(new MultiplayerScreen(new TitleScreen()), "disconnect.lost", new TranslatableText("Was not able to reconnect!"), serverInfo));
        }
    }

}