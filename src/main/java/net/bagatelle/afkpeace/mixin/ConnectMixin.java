package net.bagatelle.afkpeace.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.bagatelle.afkpeace.util.DisconnectRetryScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Text;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {

    public String currentServer;

    @Inject(method="onGameJoin", at=@At("RETURN"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ServerInfo serverData = mc.getCurrentServerEntry();
        if(serverData == null) {
            currentServer = null;
        } else {
            currentServer = serverData.address;
        }
    }

    @Inject(method="onDisconnected", at=@At("HEAD"), cancellable=true)
    public void setAFKPeaceDisconnectScreen(Text reason, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(reason.getString().contains("Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host")) {
            mc.openScreen(new DisconnectRetryScreen(new MultiplayerScreen(null), "disconnect.lost", reason, (String)null, (Integer)(null)));
            cbi.cancel();
        }
        System.out.println(reason.getString());
    }
}