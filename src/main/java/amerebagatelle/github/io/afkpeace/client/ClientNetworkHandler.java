package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.common.Packets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ClientNetworkHandler {
    public static final ClientNetworkHandler INSTANCE = new ClientNetworkHandler();

    public void processPacket(CustomPayloadS2CPacket packet) {
        Identifier channel = packet.getChannel();
        if (channel.equals(Packets.AFKPEACE_DISABLE)) {
            AFKPeaceClient.disabled = true;
            MinecraftClient.getInstance().player.sendSystemMessage(new TranslatableText("afkpeace.disconnectscreen.disabled"), Util.NIL_UUID);
        }
    }
}
