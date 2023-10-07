package com.jaskarth.ecotones.mixin.client;

import com.jaskarth.ecotones.client.render.EcotonesRenderLayers;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL32;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.client.ClientSidedServerData;
import com.jaskarth.ecotones.client.CloudHandler;
import com.jaskarth.ecotones.client.sky.SkyboxGenerator;

import java.util.Random;
import java.util.function.Supplier;

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

    @Shadow @Final private MinecraftClient client;

    @Shadow private double lastTranslucentSortX;

    @Shadow private double lastTranslucentSortY;

    @Shadow private double lastTranslucentSortZ;

    @Shadow @Final private ObjectArrayList<WorldRenderer.ChunkInfo> chunkInfos;

    @Shadow private @Nullable ChunkBuilder chunkBuilder;

    private void registerClouds(TextureManager textureManager) {
        NativeImage image = new NativeImage(256, 256, false);

        Random random = new Random();
        PerlinNoiseSampler noise = new PerlinNoiseSampler(new CheckedRandom(random.nextLong()));

        for (int x = 0; x < 256; x++) {
            for (int z = 0; z < 256; z++) {
                if (noise.sample(x / 16.0, 0, z / 16.0) * 2.5 < random.nextDouble()) {
                    image.setColor(x, z, COLOR);
                }
            }
        }

        NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
        textureManager.registerTexture(CLOUDS, texture);
        CloudHandler.setTexture(texture);
    }

//    @Inject(method = "render", at = @At("HEAD"))
    private void polygon(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        GL21.glPointSize(4.0f);
        GL21.glPolygonMode(GL21.GL_FRONT_AND_BACK, GL21.GL_LINE);
    }

//    @Inject(method = "render", at = @At("TAIL"))
    private void polygone(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        GL21.glPolygonMode(GL21.GL_FRONT_AND_BACK, GL21.GL_FILL);
    }

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BackgroundRenderer;clearFog()V", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void renderEcotonesFancyStars(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (ClientSidedServerData.isInEcotonesWorld && Ecotones.CONFIG.client.useEcotonesSky) {
            if (!this.initializedStars) {
                this.starsBuffer.close();
                this.starsBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();

                SkyboxGenerator.renderStars(buffer);

                this.starsBuffer.bind();
                this.starsBuffer.upload(buffer.end());
                VertexBuffer.unbind();
                this.initializedStars = true;
            }
        }
    }
}
