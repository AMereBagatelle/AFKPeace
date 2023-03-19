package amerebagatelle.github.io.afkpeace.mixin.client;

import amerebagatelle.github.io.afkpeace.AFKManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onInputTick(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        AFKManager.afkTime = 0;
    }
}
