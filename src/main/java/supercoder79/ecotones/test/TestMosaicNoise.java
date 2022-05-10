package supercoder79.ecotones.test;

import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;

import java.util.Random;

public final class TestMosaicNoise {
    public static void main(String[] args) {
        Random random = new Random();
        OctaveNoiseSampler<OpenSimplexNoise> mainNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 3, 64, 1, 1);
        OctaveNoiseSampler<OpenSimplexNoise> addedInterp = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 1, 16, 1, 1);

        ImageDumper.dumpImage("mosaic.png", 2048, (x, z) -> {
            double mainNoiseValue = mainNoise.sample(x, z) - 0.1;
            double interp = addedInterp.sample(x, z) * 0.4;

            double value = mainNoiseValue + interp;

            value = MathHelper.clamp(value, 0, 1);

            int val = (int) (value * 8);

            val *= 32;

            return ImageDumper.getIntFromColor(val, val, val);
        });
    }
}
