package com.example.mixin.client;

import com.example.ClientPlayNetworkHandlerData;
import com.example.ExampleModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    /*
     * More Shulker quick open stuff
     */
    @Inject(at = @At("HEAD"), method = "setScreen")
    private void setScreen(Screen screen, CallbackInfo info) {
        if (!ExampleModClient.connectedToServer()) return;

        /*
            So apparently what happens for shulker quickopen is
            1. closeScreen -> setScreen
            2. setScreen(from before) -> lockCursor -> setScreen(null)
            3. openScreen -> setScreen
            This apparently comes from ClientPlayNetworkHandler

            Normal path
            1. closeScreen -> setScreen(null)
            2. setScreen(from before) -> lockCursor -> setScreen(null)
            3. Screen.close -> setScreen(null)
            (Opens shulker)
            4. openScreen -> setScreen(ShulkerBoxScreen)
            This apparently comes from Screen.close from Screen.keyPressed
         */
        ClientPlayNetworkHandlerData.interrupt();
    }

    /*
     * Prevent hand from swinging when dropping item (i.e no left click while pressing Q)
     *
    @Redirect(at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"), method="handleInputEvents")
    public void swingHand(ClientPlayerEntity instance, Hand hand) {
        if (!ExampleModClient.connectedToServer()) instance.swingHand(hand);
    }
     */
}
