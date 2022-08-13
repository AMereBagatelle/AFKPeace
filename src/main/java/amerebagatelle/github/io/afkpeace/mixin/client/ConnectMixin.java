package amerebagatelle.github.io.afkpeace.mixin.client;

import amerebagatelle.github.io.afkpeace.AFKManager;
import amerebagatelle.github.io.afkpeace.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.ConnectionManager;
import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {
    @Shadow @Final
    private MinecraftClient client;
    private float lastHealth;

    /**
     * Checks if we should try to automatically reconnect, and if not opens a custom screen with a reconnect button
     */
    @Environment(EnvType.CLIENT)
    @Inject(method = "onDisconnected", at = @At("HEAD"), cancellable = true)
    public void tryReconnect(Text reason, CallbackInfo cbi) {
        if(AFKPeaceClient.loginScreen instanceof RealmsScreen) {
            client.setScreen(new RealmsMainScreen(new TitleScreen()));
            return;
        }

        if (!(AFKPeaceConfigManager.RECONNECT_ENABLED.value() || AFKManager.isAfk())) return;
        if (reason.toString().contains("multiplayer.disconnect.kicked")) return;

        ServerInfo target = AFKPeaceClient.currentServerEntry;
        if (!ConnectionManager.isDisconnecting) {
            if (target != null) {
                ConnectionManager.startReconnect(target);
                cbi.cancel();
            }
        } else {
            ConnectionManager.isDisconnecting = false;
        }
    }

    /**
     * Gets when the player's health changes, and logs the player out if it has taken damage
     */
    @Environment(EnvType.CLIENT)
    @Inject(method = "onHealthUpdate", at = @At("TAIL"))
    public void onPlayerHealthUpdate(HealthUpdateS2CPacket packet, CallbackInfo cbi) {
        if (AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED.value() || AFKManager.isAfk()) {
            try {
                if (packet.getHealth() < lastHealth && packet.getHealth() < AFKPeaceConfigManager.DAMAGE_LOGOUT_TOLERANCE.value()) {
                    ConnectionManager.disconnectFromServer(Text.translatable("afkpeace.reason.damagelogout"));
                }
            } catch (NullPointerException ignored) {
            }
        }
        lastHealth = packet.getHealth();
    }
}