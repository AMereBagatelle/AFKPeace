package amerebagatelle.github.io.afkpeace.mixin;

import amerebagatelle.github.io.afkpeace.AFKManager;
import amerebagatelle.github.io.afkpeace.ConnectionManager;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    private int trackingTick;

    @Shadow
    public abstract float getTickDelta();

    /**
     * Used to activate the reconnection when the reconnection thread is done.
     */
    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        ConnectionManager connectionManager = ConnectionManager.INSTANCE;
        if (connectionManager.isReconnecting) {
            ConnectionManager.INSTANCE.finishReconnect();
            connectionManager.isReconnecting = false;
        }
        if (Boolean.parseBoolean(SettingsManager.loadSetting("autoAfk"))) {
            AFKManager.INSTANCE.tickAfkStatus();
        }
    }
}