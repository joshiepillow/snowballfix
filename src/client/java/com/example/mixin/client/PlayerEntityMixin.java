package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    /*
     * Prevent dropping item while in inventory menu from swinging hand
     */
    @Redirect(at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"), method="dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    public void swingHand(PlayerEntity instance, Hand hand) {
        if (!ExampleModClient.connectedToServer()) instance.swingHand(hand);
    }
}
