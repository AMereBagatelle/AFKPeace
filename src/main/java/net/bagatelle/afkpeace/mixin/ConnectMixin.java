package net.bagatelle.afkpeace.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Text;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {

    @Inject(method="onGameJoin", at=@At("RETURN"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        System.out.println(packet);
    }
    
    @Inject(method="onDisconnected", at=@At("RETURN"))
    public void onDisconnectFromServerEvent(Text reason, CallbackInfo cbi) {
        System.out.println(reason);
    }
}