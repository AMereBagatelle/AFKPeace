package amerebagatelle.github.io.afkpeace.mixin;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {
    private float lastHealth;

    // Sets the server data so that we know what to reconnect to.
    @Environment(EnvType.CLIENT)
    @Inject(method="onGameJoin", at=@At("HEAD"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        AFKPeace.currentServerEntry = MinecraftClient.getInstance().getCurrentServerEntry();
    }

    // Checks if we should try to automatically reconnect, and if not opens a custom screen with a reconnect button
    @Environment(EnvType.CLIENT)
    @Inject(method = "onDisconnected", at = @At("HEAD"), cancellable = true)
    public void tryReconnect(Text reason, CallbackInfo cbi) {
        ServerInfo target = AFKPeace.currentServerEntry;
        if (Boolean.parseBoolean(SettingsManager.loadSetting("reconnectEnabled"))) {
            if (!AFKPeace.getConnectionManager().isDisconnecting) {
                if (target != null) {
                    AFKPeace.getConnectionManager().startReconnect(target);
                    cbi.cancel();
                }
            } else {
                AFKPeace.getConnectionManager().isDisconnecting = false;
            }
        }
    }

    // Gets when the player's health changes, and logs the player out if it has taken damage
    @Environment(EnvType.CLIENT)
    @Inject(method="onHealthUpdate", at=@At("TAIL"))
    public void onPlayerHealthUpdate(HealthUpdateS2CPacket packet, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (Boolean.parseBoolean(SettingsManager.loadSetting("damageLogoutEnabled"))) {
            try {
                if (packet.getHealth() < lastHealth) {
                    AFKPeace.getConnectionManager().disconnectFromServer(new LiteralText("Damage logout activated"));
                }
            } catch (NullPointerException ignored) {
            }
        }
        lastHealth = packet.getHealth();
    }
}