package com.example.mixin.client;

import com.example.ClientPlayNetworkHandlerData;
import com.example.ExampleModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
    On Shulker quick open, prevents the minecraft client from unnecessarily setting the cursor to the
    center of the screen.
 */
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow
    @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "onCloseScreen")
    private void onCloseScreen(CallbackInfo callbackInfo) {
        if (!ExampleModClient.connectedToServer()) return;

        if (client.currentScreen instanceof HandledScreen<?>) {
            ClientPlayNetworkHandlerData.noInterrupt = true;
            ClientPlayNetworkHandlerData.disableInterrupt = true;
            ClientPlayNetworkHandlerData.x = client.mouse.getX();
            ClientPlayNetworkHandlerData.y = client.mouse.getY();
        }
    }

    @Inject(at = @At("RETURN"), method = "onCloseScreen")
    private void onCloseScreenEnd(CallbackInfo callbackInfo) {
        if (!ExampleModClient.connectedToServer()) return;

        ClientPlayNetworkHandlerData.disableInterrupt = false;
    }

    @Inject(at = @At("HEAD"), method = "onOpenScreen")
    private void onOpenScreen(CallbackInfo callbackInfo) {
        if (!ExampleModClient.connectedToServer()) return;

        ClientPlayNetworkHandlerData.disableInterrupt = true;
    }

    @Inject(at = @At("RETURN"), method = "onOpenScreen")
    private void onOpenScreenEnd(CallbackInfo callbackInfo) {
        if (!ExampleModClient.connectedToServer()) return;

        if (client.currentScreen instanceof HandledScreen<?> && ClientPlayNetworkHandlerData.noInterrupt) {
            ((MouseAccessor) client.mouse).setX(ClientPlayNetworkHandlerData.x);
            ((MouseAccessor) client.mouse).setY(ClientPlayNetworkHandlerData.y);
            InputUtil.setCursorParameters(client.getWindow().getHandle(), 212993, ClientPlayNetworkHandlerData.x, ClientPlayNetworkHandlerData.y);
        }
        ClientPlayNetworkHandlerData.disableInterrupt = false;
        ClientPlayNetworkHandlerData.noInterrupt = false;
    }
}
