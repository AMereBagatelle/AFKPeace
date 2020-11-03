package amerebagatelle.github.io.afkpeace.mixin.client;

import amerebagatelle.github.io.afkpeace.client.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.client.ConnectionManager;
import amerebagatelle.github.io.afkpeace.common.Packets;
import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {
    @Shadow
    private MinecraftClient client;
    private float lastHealth;

    /**
     * Gathers server data so that we know what to reconnect to.
     */
    @Environment(EnvType.CLIENT)
    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        AFKPeaceClient.currentServerEntry = client.getCurrentServerEntry();
        AFKPeaceClient.disabled = false;
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        Objects.requireNonNull(client.player);
        buf.writeUuid(client.player.getUuid());
        client.player.networkHandler.getConnection().send(new CustomPayloadC2SPacket(Packets.AFKPEACE_HELLO, buf));
    }

    /**
     * Checks if we should try to automatically reconnect, and if not opens a custom screen with a reconnect button
     */
    @Environment(EnvType.CLIENT)
    @Inject(method = "onDisconnected", at = @At("HEAD"), cancellable = true)
    public void tryReconnect(Text reason, CallbackInfo cbi) {
        if (!AFKPeaceClient.disabled) {
            ConnectionManager connectionManager = ConnectionManager.INSTANCE;
            ServerInfo target = AFKPeaceClient.currentServerEntry;
            String reasonString = reason.toString();
            if (Boolean.parseBoolean(SettingsManager.loadSetting("reconnectEnabled"))) {
                if (!reasonString.contains("multiplayer.disconnect.kicked")) {
                    if (!connectionManager.isDisconnecting) {
                        if (target != null) {
                            connectionManager.startReconnect(target);
                            cbi.cancel();
                        }
                    } else {
                        connectionManager.isDisconnecting = false;
                    }
                }
            }
        }
    }

    /**
     * Gets when the player's health changes, and logs the player out if it has taken damage
     */
    @Environment(EnvType.CLIENT)
    @Inject(method = "onHealthUpdate", at = @At("TAIL"))
    public void onPlayerHealthUpdate(HealthUpdateS2CPacket packet, CallbackInfo cbi) {
        if (!AFKPeaceClient.disabled) {
            if (Boolean.parseBoolean(SettingsManager.loadSetting("damageLogoutEnabled"))) {
                try {
                    if (packet.getHealth() < lastHealth && packet.getHealth() < Integer.parseInt(SettingsManager.loadSetting("damageLogoutTolerance"))) {
                        ConnectionManager.INSTANCE.disconnectFromServer(new TranslatableText("reason.damagelogout"));
                    }
                } catch (NullPointerException ignored) {
                }
            }
            lastHealth = packet.getHealth();
        }
    }
}