package supercoder79.ecotones.world.data;

import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;

import java.util.Random;

public final class Mosaic implements DataFunction {
    private final int max;
    private final double mainOffset;
    private final double addScale;
    private final OctaveNoiseSampler<OpenSimplexNoise> mainNoise;
    private final OctaveNoiseSampler<OpenSimplexNoise> addedInterp;

    public Mosaic(long seed, int max, double mainSpread, double addSpread, double mainOffset, double addScale) {
        Random random = new Random(seed);
        this.mainNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 3, mainSpread, 1, 1);
        this.addedInterp = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 1, addSpread, 1, 1);
        this.max = max;
        this.mainOffset = mainOffset;
        this.addScale = addScale;
    }

    @Override
    public double get(double x, double z) {
        double mainNoiseValue = this.mainNoise.sample(x, z) - this.mainOffset;
        double interp = this.addedInterp.sample(x, z) * this.addScale;

        double value = mainNoiseValue + interp;

        value = MathHelper.clamp(value, 0, 1);

        return (int) (value * this.max);
    }
}
