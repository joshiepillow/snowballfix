package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ItemCooldownManager.class)
public abstract class ItemCooldownManagerMixin {
    @Shadow public abstract boolean isCoolingDown(Item item);

    /*
     * Prevent server from putting item on cooldown if it is already on cooldown (due to lag)
     */

    //delete - not useful
    /*
    @Inject(at=@At("HEAD"), method="set", cancellable = true)
    public void set(Item item, int duration, CallbackInfo ci) {
        if (ExampleModClient.connectedToServer() && isCoolingDown(item)) {
            ci.cancel();
        }
    }
     */
}
