package net.bagatelle.afkpeace.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.MinecraftClient;
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
            currentServer = serverData.name;
        }
    }
    
    @Inject(method="onDisconnected", at=@At("RETURN"))
    public void onDisconnectFromServerEvent(Text reason, CallbackInfo cbi) {
        if(reason.getString().contains("Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host")) {
            System.out.println("It worked");
        }
        System.out.println(reason.getString());
    }
}