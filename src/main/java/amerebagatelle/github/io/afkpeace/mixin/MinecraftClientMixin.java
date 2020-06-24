package amerebagatelle.github.io.afkpeace.mixin;

import amerebagatelle.github.io.afkpeace.ConnectionManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        ConnectionManager connectionManager = ConnectionManager.INSTANCE;
        if (connectionManager.isReconnecting) {
            ConnectionManager.INSTANCE.finishReconnect();
            connectionManager.isReconnecting = false;
        }
    }
}