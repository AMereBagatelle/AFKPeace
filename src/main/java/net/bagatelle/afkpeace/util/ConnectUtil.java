package net.bagatelle.afkpeace.util;

import net.bagatelle.afkpeace.miscellaneous.DisconnectRetryScreen;
import net.bagatelle.afkpeace.miscellaneous.ReconnectTestThread;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ConnectUtil {

    public int reconnectTimer = 0;

    // Tries to connect to server, and if it doesn't have a serverInfo entry to connect to just boots to multiplayer screen
    @Environment(EnvType.CLIENT)
    public void connectToServer(ServerInfo serverInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (serverInfo != null) {
            mc.openScreen(new ConnectScreen(new MultiplayerScreen(new TitleScreen()), mc, serverInfo));
        } else {
            mc.openScreen(new MultiplayerScreen(new TitleScreen()));
        }
    }

    // * Find what happens between these two methods in SetupUtil
    // Tries to reconnect to the server, sends the result to StateVariables
    @Environment(EnvType.CLIENT)
    public void startReconnect(ServerInfo serverInfo) {
        ReconnectTestThread reconnectTestThread = new ReconnectTestThread(serverInfo);
        reconnectTestThread.setName("Reconnect Thread");
        reconnectTestThread.start();
    }

    // Handles the actual connection part of the reconnect
    @Environment(EnvType.CLIENT)
    public void finishReconnect(int canConnect, ServerInfo serverInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(canConnect == 1) {
            mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverInfo));
        } else if (canConnect == 2) {
            System.out.println("Can't connect, reached max number of attempts!");
            mc.openScreen(new DisconnectRetryScreen(new MultiplayerScreen(new TitleScreen()), "disconnect.lost", new TranslatableText("Was not able to reconnect!"), serverInfo));
        }
    }

    // ! Deprecated code, handled in ConnectMixin (maybe move to here?)
    @Environment(EnvType.CLIENT)
    public void disconnectFromServer(ServerInfo serverInfo, Text reason) {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.disconnect();
        mc.openScreen(new DisconnectRetryScreen(new MultiplayerScreen(new TitleScreen()), "disconnect.lost", reason, serverInfo));
    }

}