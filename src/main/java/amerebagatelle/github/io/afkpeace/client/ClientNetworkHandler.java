package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.common.Packets;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;

public class ClientNetworkHandler {
    public static final ClientNetworkHandler INSTANCE = new ClientNetworkHandler();

    public void processPacket(CustomPayloadS2CPacket packet) {
        Identifier channel = packet.getChannel();
        if (channel == Packets.AFKPEACE_DISABLE) {
            AFKPeaceClient.disabled = true;
        }
    }
}
