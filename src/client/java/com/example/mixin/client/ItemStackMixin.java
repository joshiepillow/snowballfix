package com.example.mixin.client;

import com.example.SnowballFixModClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;

/*
    Prevents items with infinity enchant from being used client-side preventing client-server desync (hopefully).
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(at = @At("HEAD"), method = "decrement", cancellable = true)
    private void decrement(int amount, CallbackInfo callbackInfo) {
        if (!SnowballFixModClient.connectedToServer()) return;

        StackWalker.StackFrame frame = StackWalker.getInstance().walk(stream->stream.limit(3).collect(Collectors.toList())).get(2);
        if (frame.getMethodName().equals("use") && EnchantmentHelper.getLevel(Enchantments.INFINITY, ((ItemStack) (Object) this)) > 0) {
            callbackInfo.cancel();
        }
    }
}
