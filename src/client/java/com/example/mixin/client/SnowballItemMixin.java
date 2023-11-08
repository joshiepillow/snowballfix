package com.example.mixin.client;

import net.minecraft.item.SnowballItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowballItem.class)
public class SnowballItemMixin {
	/*
		This is an annoying fix for snowballs disappearing on use in particular.
		Should be merged into the general ItemStackMixin for items with infinity enchants as soon
		as Riptide Missile gets infinity.

		Edit: Should be fixed now
	 */
	/*
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), method = "use")
	private void decrement(ItemStack instance, int amount) {
		if (!SnowballFixMod.connectedToServer()) instance.decrement(1);;
		if(instance.hasEnchantments()) {
			return;
		}
		instance.decrement(1);
	}
	 */
}

