package com.example.mixin.client;

import com.example.SnowballFixModClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/*
    An imperfect fix for client-server desync when right-clicking items in the inventory with custom behavior.
    Currently disables right-click pickup for any stack with only one item because why would anyone right-click
    a single stack?!?
 */
@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Redirect(method="internalOnSlotClick", at=@At(value="INVOKE", target="Lnet/minecraft/screen/slot/Slot;tryTakeStackRange(IILnet/minecraft/entity/player/PlayerEntity;)Ljava/util/Optional;", ordinal=0))
    private Optional<ItemStack> tryTakeStackRange(Slot slot, int min, int max, PlayerEntity player, int slotIndex, int button, SlotActionType actionType, PlayerEntity player2) {
        if (!SnowballFixModClient.connectedToServer()) return slot.tryTakeStackRange(min, max, player);
        if (button == 0 || slot.getStack().getCount() > 1) return slot.tryTakeStackRange(min, max, player);
        return Optional.empty();
    }
}
