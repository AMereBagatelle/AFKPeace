package net.bagatelle.afkpeace.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.bagatelle.afkpeace.AFKPeace;
import net.bagatelle.afkpeace.miscellaneous.DisconnectRetryScreen;
import net.bagatelle.afkpeace.settings.SettingsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {

    // Sets the server data so that we know what to reconnect to.
    @Environment(EnvType.CLIENT)
    @Inject(method="onGameJoin", at=@At("HEAD"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ServerInfo serverData = mc.getCurrentServerEntry();
        if(serverData != null) {
            AFKPeace.stateVariables.currentServer = serverData;
        }
    }

    // Checks if we should try to automatically reconnect, and if not opens a custom screen with a reconnect button
    @Environment(EnvType.CLIENT)
    @Inject(method="onDisconnected", at=@At("HEAD"), cancellable=true)
    public void setAFKPeaceDisconnectScreen(Text reason, CallbackInfo cbi) {
        System.out.println(reason.toString());
        MinecraftClient mc = MinecraftClient.getInstance();
        if(AFKPeace.stateVariables.currentServer != null) {
            mc.disconnect();
            if(SettingsManager.isReconnectOnTimeoutActive && !reason.toString().contains("multiplayer.disconnect.kicked") || !reason.toString().contains("multiplayer.disconnect.banned")) {
                AFKPeace.connectUtil.startReconnect(AFKPeace.stateVariables.currentServer);
            } else {
                mc.openScreen(new DisconnectRetryScreen(new MultiplayerScreen(new TitleScreen()), "disconnect.lost", reason, AFKPeace.stateVariables.currentServer));
            }
            cbi.cancel();
        }
    }

    // Gets when the player's health changes, and logs the player out if it has taken damage
    @Environment(EnvType.CLIENT)
    @Inject(method="onHealthUpdate", at=@At("TAIL"))
    public void onPlayerHealthUpdate(HealthUpdateS2CPacket packet, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(packet.getHealth() != mc.player.getMaximumHealth() && SettingsManager.isDamageProtectActive) {
            mc.getNetworkHandler().getConnection().disconnect(new TranslatableText("Logged out on damage"));
            mc.openScreen(new DisconnectRetryScreen(new MultiplayerScreen(new TitleScreen()), "disconnect.lost", new TranslatableText("Saved ya"), AFKPeace.stateVariables.currentServer));
            SettingsManager.isDamageProtectActive = false;
        }
    }
}