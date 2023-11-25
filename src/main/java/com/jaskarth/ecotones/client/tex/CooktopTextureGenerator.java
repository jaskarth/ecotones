package com.jaskarth.ecotones.client.tex;

import com.jaskarth.ecotones.Ecotones;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;

public final class CooktopTextureGenerator {
    public static final Identifier ID = Ecotones.id("generated/brick_cooktop_front/on");
    private static DynamicTexture TEXTURE;
    public static float[] lastFlameIntensities = new float[320];
    public static float[] currFlameIntensities = new float[320];
    private static int intenseFlameTicks;


    private static NativeImage INNER;

    public static AbstractTexture register(TextureManager manager) {
        ResourceTexture.TextureData data = ResourceTexture.TextureData.load(MinecraftClient.getInstance().getResourceManager(), Ecotones.id("textures/block/brick_cooktop_front.png"));
        try {
            INNER = data.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NativeImage image = new NativeImage(16, 16, false);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                image.setColor(x, z, 0xFFFFFFFF);
            }
        }

        TEXTURE = new DynamicTexture(image, CooktopTextureGenerator::tick);

        manager.registerTexture(Ecotones.id("generated/brick_cooktop_front/on"), TEXTURE);
        return TEXTURE;
    }

    private static void tick() {
        if (Math.random() < 0.1) {
            intenseFlameTicks = 5;
        }
        intenseFlameTicks--;

        for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 20; ++z) {
                int intensityScaling = 16;
                float curIntensity = lastFlameIntensities[x * 20 + ((z + 1) % 20)] * (float)intensityScaling;
                if (intenseFlameTicks >= 1) {
                    curIntensity *= 1.03;
                } else if (intenseFlameTicks == 0) {
                    curIntensity *= 1.01;
                }

                intensityScaling += 1;
                if (z < 14) {
                    intensityScaling++;
                }

                // Find neighbor pixel intensities
                for(int ax = x - 1; ax <= x + 1; ++ax) {
                    for(int az = z; az <= z + 1; ++az) {
                        // Make sure we're in the play field
                        if (ax >= 0 && az >= 0 && ax < 16 && az < 20) {
                            curIntensity += lastFlameIntensities[ax * 20 + az];
                        }

                        ++intensityScaling;
                    }
                }

                currFlameIntensities[x * 20 + z] = curIntensity / ((float)intensityScaling * 1.05F);

                // Produce random variation at the bottom
                if (z == 19 || (z == 18 && Math.random() < 0.02)) {
                    if (Math.random() < 0.04) {
                        continue;
                    }

                    currFlameIntensities[x * 20 + z] = (float)(Math.random() * Math.random() * Math.random() * 2.0 + Math.random() * 0.15 + 0.5);
                }
            }
        }

        float[] temp = currFlameIntensities;
        currFlameIntensities = lastFlameIntensities;
        lastFlameIntensities = temp;

        NativeImage image = TEXTURE.getImage();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float intensity = MathHelper.clamp(lastFlameIntensities[x * 20 + z] * 1.8F, 0, 1);

                int a = intensity < 0.5f ? 0 : 255;
                int r = (int)(intensity * 175.0F + 80.0F);
                int g = (int)(intensity * intensity * 250.0F);
                int b = (int)(Math.pow(40, 4 * (intensity - 1.0)) * 155);

                int border = Math.max(4 - (z - 8) / 2, 2);
                if (z > 9 && a > 0 && x > border && x < (15 - border)) {
                    // ABGR
                    image.setColor(x, z, 0xFF000000 | (b << 16) | (g << 8) | r);
                } else {
                    int color = INNER.getColor(x, z);
                    image.setColor(x, z, color);
                }
            }
        }

        TEXTURE.upload();
    }
}
