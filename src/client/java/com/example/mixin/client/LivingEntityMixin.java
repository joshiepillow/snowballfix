package com.example.mixin.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// delete later
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(at=@At("HEAD"), method="swingHand(Lnet/minecraft/util/Hand;Z)V")
    public void swingHand(Hand hand, boolean fromServerPlayer, CallbackInfo ci) {

    }
}
