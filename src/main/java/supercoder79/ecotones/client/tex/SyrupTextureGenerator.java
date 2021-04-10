package supercoder79.ecotones.client.tex;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.Ecotones;

public final class SyrupTextureGenerator {
    public static final Identifier ID = Ecotones.id("generated/tex/syrup");
    private static final int WHITE = 255 << 24 | 255 << 16 | 255 << 8 | 255;

    private static final DynamicTexture TEXTURE;

    static {
        NativeImage image = new NativeImage(16, 16, false);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                image.setPixelColor(x, z, WHITE);
            }
        }

        TEXTURE = new DynamicTexture(image, SyrupTextureGenerator::tick);

        MinecraftClient.getInstance().getTextureManager().registerTexture(ID, TEXTURE);
    }

    // Particle phases for the current texture, from 0 to 3
    // Expands outwards from a central node, creating semigeometrical shapes
    private static final int[] PARTICLE_PHASE = new int[16 * 16];

    private static int ticks = 0;
    private static void tick() {
        // Update texture every half second
        ticks++;
        if (ticks < 10) {
            return;
        }
        ticks = 0;

        // This is a 2 layer cellular automata used for generating syrup textures dynamically. It's roughly based on how lava and water used to work before 1.5.
        // First, the main automata samples all the texels in a 3x3 grid locally and averages them, giving the center a larger weight.
        // Second, the background automata creates 1x1, 3x3, and 5x5 squares of "particle phases" that darken the main automata texels.
        // The larger the radius, the smaller the darkening effect so it has an effect of moving outward.

        NativeImage image = TEXTURE.getImage();

        int[] colors = image.makePixelArray();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Get information for this texel
                int color = colors[z + (x * 16)] & 255;
                int phase = PARTICLE_PHASE[z + (x * 16)];

                // Closer to the phase center the more reduction
                if (phase > 0) {
                    color -= (20 + (phase * 10));
                }

                // Randomly introduce phase entropy
                boolean changedPhase = false;
                if (Math.random() < 0.01) {
                    PARTICLE_PHASE[z + (x * 16)] = 3;
                    changedPhase = true;
                }

                double interpolation = 0;
                double weight = 0;

                int maxPhase = 0;
                for (int x1 = -1; x1 <= 1; x1++) {
                    for (int z1 = -1; z1 <= 1; z1++) {

                        // Interpolate, weight the center more than the edges
                        if (x1 == 0 && z1 == 0) {
                            interpolation += color * 0.2;
                            weight += 0.2;
                        } else {
                            // Get wrapped coords for the local texel
                            int sx1 = (x + x1) & 15;
                            int sz1 = (z + z1) & 15;

                            // Find the maximum phase around the neighbors
                            int sphase = PARTICLE_PHASE[sz1 + (sx1 * 16)] - 1;
                            if (sphase > maxPhase) {
                                maxPhase = sphase;
                            }

                            // Add edge interpolation
                            interpolation += (colors[sz1 + (sx1 * 16)] & 255) * 0.1;
                            weight += 0.1;
                        }
                    }
                }

                // Set the current phase if there was no phase change
                if (!changedPhase) {
                    PARTICLE_PHASE[z + (x * 16)] = maxPhase;
                }

                // Scale based on weight
                interpolation /= weight;

                // Ensure the color doesn't approach black
                interpolation += 3;

                // Cast and clamp
                int finalColor = (int)(interpolation);
                finalColor = MathHelper.clamp(finalColor, 0, 255);
                int finalRed = MathHelper.clamp((int)(finalColor * 1.25), 0, 255);

                // Set RGB to this color, color is handled by the BER (ARGB format)
                image.setPixelColor(x, z, (255 << 24) | (finalRed << 16) | (finalColor << 8) | finalColor);
            }
        }

        // Upload our changes
        TEXTURE.upload();
    }
}
