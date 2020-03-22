package net.bagatelle.afkpeace.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.bagatelle.afkpeace.miscellaneous.PlayerShadowPauseMenuScreen;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method="openPauseMenu", at=@At("HEAD"), cancellable=true)
    public void addPlayerShadowButton(boolean bl, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen == null && mc.player.networkHandler.getCommandDispatcher().findNode(Arrays.asList("player")) != null) {
            boolean bl2 = mc.isIntegratedServerRunning() && !mc.getServer().isRemote();
            if (bl2) {
                mc.openScreen(new PlayerShadowPauseMenuScreen(!bl));
                mc.getSoundManager().pauseAll();
            } else {
                mc.openScreen(new PlayerShadowPauseMenuScreen(true));
            }
            cbi.cancel();
        }
    }
}