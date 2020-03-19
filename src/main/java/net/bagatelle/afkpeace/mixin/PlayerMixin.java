package net.bagatelle.afkpeace.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.bagatelle.afkpeace.AFKPeace;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerMixin {

    @Inject(method="damage", at=@At("TAIL"), cancellable = true)
    public void onDamage(float amount, CallbackInfoReturnable<Boolean> cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(AFKPeace.damageUtil.isActive && amount != 0.0F) {
            
            cbi.setReturnValue(false);
        }
    }

}