package amerebagatelle.github.io.afkpeace.mixin.client;

import amerebagatelle.github.io.afkpeace.AFKManager;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"))
    public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        AFKManager.afkTime = 0;
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"))
    public void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        AFKManager.afkTime = 0;
    }

    @Inject(method = "onCursorPos", at = @At("HEAD"))
    public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
        AFKManager.afkTime = 0;
    }
}
