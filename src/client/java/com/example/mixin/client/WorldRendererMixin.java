package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private Framebuffer entityOutlinesFramebuffer;

    @Shadow
    private PostEffectProcessor entityOutlinePostProcessor;


    @Redirect(method="drawEntityOutlinesFramebuffer", at=@At(value="INVOKE", target="Lnet/minecraft/client/gl/Framebuffer;draw(IIZ)V"))
    public void draw(Framebuffer instance, int width, int height, boolean disableBlend) {
        instance.draw(width, height, disableBlend);
        ExampleModClient.LOGGER.info(entityOutlinePostProcessor);
    }
}
