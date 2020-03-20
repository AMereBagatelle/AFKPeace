package net.bagatelle.afkpeace.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.bagatelle.afkpeace.AFKPeace;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;

@Mixin(ClientPlayerEntity.class)
public class PlayerMixin {

    @Inject(method = "updateHealth", at = @At(value = "HEAD"), cancellable = true)
    public void onPlayerHealthUpdate(float f, CallbackInfo cbi) {
        System.out.println("Health updated");
        MinecraftClient mc = MinecraftClient.getInstance();
        if(AFKPeace.activeStates.isDamageProtectActive && mc.player.getHealth() != mc.player.getMaximumHealth() && AFKPeace.canDisconnect) {
            System.out.println("Disconnecting");
            mc.disconnect();
            mc.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), "AutoDamageLogout", new TranslatableText("I saved you.")));
            AFKPeace.canDisconnect = false;
            cbi.cancel();
        }
    }
}