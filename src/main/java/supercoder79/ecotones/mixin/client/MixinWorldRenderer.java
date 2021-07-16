package supercoder79.ecotones.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.gen.SimpleRandom;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.client.ClientSidedServerData;
import supercoder79.ecotones.client.CloudHandler;
import supercoder79.ecotones.client.sky.SkyboxGenerator;

import java.util.Random;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    private static final int COLOR = 255 << 24 | 255 << 16 | 255 << 8 | 255;

    // TODO: not reset on loading a new world
    @Unique
    private boolean initializedClouds = false;

    @Unique
    private boolean initializedStars = false;

    @Shadow @Final private static Identifier CLOUDS;

    @Shadow @Nullable private VertexBuffer starsBuffer;

//    @Redirect(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"))
//    private void bindEcotonesFancyClouds(int i, Identifier id) {
//        // TODO: disabled
////        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
////        if (ClientSidedServerData.isInEcotonesWorld) {
////            if (!this.initializedClouds) {
////                registerClouds(textureManager);
////                this.initializedClouds = true;
////            }
////
////            CloudHandler.update();
////        }
//
//        RenderSystem._setShaderTexture(i, id);
//    }

    private void registerClouds(TextureManager textureManager) {
        NativeImage image = new NativeImage(256, 256, false);

        Random random = new Random();
        PerlinNoiseSampler noise = new PerlinNoiseSampler(new SimpleRandom(random.nextLong()));

        for (int x = 0; x < 256; x++) {
            for (int z = 0; z < 256; z++) {
                if (noise.sample(x / 16.0, 0, z / 16.0) * 2.5 < random.nextDouble()) {
                    image.setPixelColor(x, z, COLOR);
                }
            }
        }

        NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
        textureManager.registerTexture(CLOUDS, texture);
        CloudHandler.setTexture(texture);
    }

    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/VertexBuffer;setShader(Lnet/minecraft/util/math/Matrix4f;Lnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/Shader;)V", ordinal = 1, shift = At.Shift.BEFORE), cancellable = true)
    private void renderEcotonesFancyStars(MatrixStack matrices, Matrix4f matrix4f, float f, Runnable runnable, CallbackInfo ci) {
        if (ClientSidedServerData.isInEcotonesWorld) {
            if (!this.initializedStars) {
                this.starsBuffer.close();
                this.starsBuffer = new VertexBuffer();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();

                SkyboxGenerator.renderStars(buffer);

                buffer.end();
                this.starsBuffer.upload(buffer);
                this.initializedStars = true;
            }
        }
    }
}
