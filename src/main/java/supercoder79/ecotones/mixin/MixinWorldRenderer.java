package supercoder79.ecotones.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.gen.SimpleRandom;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import supercoder79.ecotones.client.ClientSidedServerData;
import supercoder79.ecotones.client.CloudHandler;

import java.util.Random;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    private static final int COLOR = 255 << 24 | 255 << 16 | 255 << 8 | 255;
    @Unique
    private boolean initialized = false;

    @Shadow @Final private static Identifier CLOUDS;

    @Redirect(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;FDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V"))
    private void bindEcotonesFancyClouds(TextureManager textureManager, Identifier id) {
        if (ClientSidedServerData.isInEcotonesWorld) {
            if (!this.initialized) {
                registerClouds(textureManager);
                this.initialized = true;
            }

            CloudHandler.update();
        }

        textureManager.bindTexture(id);
    }

    private void registerClouds(TextureManager textureManager) {
        NativeImage image = new NativeImage(256, 256, false);

        Random random = new Random();
        PerlinNoiseSampler noise = new PerlinNoiseSampler(new SimpleRandom(random.nextLong()));

        for (int x = 0; x < 256; x++) {
            for (int z = 0; z < 256; z++) {
                if (noise.sample(x / 16.0, 0, z / 16.0) * 1.5 < random.nextDouble()) {
                    image.setPixelColor(x, z, COLOR);
                }
            }
        }

        NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
        textureManager.registerTexture(CLOUDS, texture);
        CloudHandler.setTexture(texture);
    }
}
