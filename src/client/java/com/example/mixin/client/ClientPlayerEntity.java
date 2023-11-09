package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.client.network.ClientPlayerEntity.class)
public class ClientPlayerEntity {
    /*
     * Prevent pressing drop key while in hotbar from actually dropping item client-side (because the server cancels the event anyway)
     */
    @Redirect(method="dropSelectedItem", at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerInventory;dropSelectedItem(Z)Lnet/minecraft/item/ItemStack;"))
    public ItemStack dropSelectedItem(PlayerInventory instance, boolean entireStack) {
        if (!ExampleModClient.connectedToServer()) return instance.dropSelectedItem(entireStack);
        return ItemStack.EMPTY;
    }
}
