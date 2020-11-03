package amerebagatelle.github.io.afkpeace.mixin.server;

import amerebagatelle.github.io.afkpeace.server.ServerNetworkHandler;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerNetworkHandlerMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    public void receivePacket(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        CustomPayloadC2SPacketFake packetFake = (CustomPayloadC2SPacketFake) packet;
        if (packetFake.getChannel().getNamespace().equals("afkpeace")) {
            ServerNetworkHandler.INSTANCE.processPacket(packetFake, server);
            ci.cancel();
        }
    }
}
