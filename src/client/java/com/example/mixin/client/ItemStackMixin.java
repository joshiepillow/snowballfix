package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;

/*
    Prevents items with infinity enchant from being used client-side preventing client-server desync (hopefully).
    Also sets cooldown of items with attribute client side
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    @Final private Item item;

    @Shadow
    public abstract NbtCompound getNbt();

    @Inject(at = @At("HEAD"), method = "decrement", cancellable = true)
    private void decrement(int amount, CallbackInfo callbackInfo) {
        if (!ExampleModClient.connectedToServer()) return;

        StackWalker.StackFrame frame = StackWalker.getInstance().walk(stream->stream.limit(3).collect(Collectors.toList())).get(2);
        if (frame.getMethodName().equals("use") && EnchantmentHelper.getLevel(Enchantments.INFINITY, ((ItemStack) (Object) this)) > 0) {
            callbackInfo.cancel();
            /*
            try {
                NbtCompound a = (NbtCompound)getNbt().get("Monumenta");
                NbtCompound b = (NbtCompound)a.get("Stock");
                NbtList c = (NbtList)b.get("Attributes");
                c.stream()
                        .filter(d -> d instanceof NbtCompound)
                        .map(e ->(NbtCompound) e)
                        .filter(f -> f.contains("AttributeName") &&
                                f.get("AttributeName") instanceof NbtString g &&
                                g.asString().equals("Throw Rate") &&
                                f.contains("Amount") &&
                                f.get("Amount") instanceof NbtDouble
                        ).findFirst().map(h -> ((NbtDouble) h.get("Amount")).doubleValue())
                        .ifPresent(i -> {
                            MinecraftClient.getInstance().player.getItemCooldownManager().set(item, (int) (20/i));
                        });
            } catch (Exception ignored) {

            }
             */
        }

    }


}
