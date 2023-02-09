package supercoder79.ecotones.world.gen.caves;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import supercoder79.ecotones.world.gen.NoiseColumn;

import java.util.Random;
import java.util.stream.IntStream;

// https://github.com/SuperCoder7979/cavebiomes/blob/6037b627943efc7e7e7425264f18d92d60e02419/src/main/java/supercoder79/cavebiomes/world/carver/PerlerpCarver.java
public class OldCaveBiomesCaveGenerator implements NoiseCaveGenerator {
    private OctavePerlinNoiseSampler caveNoise;
    private OctavePerlinNoiseSampler offsetNoise;
    private OctavePerlinNoiseSampler scaleNoise;

    @Override
    public void init(long seed) {
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(seed));
        this.caveNoise = OctavePerlinNoiseSampler.create(chunkRandom, IntStream.rangeClosed(-5, 0));
        this.offsetNoise = OctavePerlinNoiseSampler.create(chunkRandom, IntStream.rangeClosed(-2, 0));
        this.scaleNoise = OctavePerlinNoiseSampler.create(chunkRandom, IntStream.rangeClosed(-0, 0));
    }

    @Override
    public void genColumn(int x, int z, NoiseColumn values) {
        double[] newVals = new double[values.buffer.length];
        sampleNoiseColumn(newVals, x, z, caveNoise, offsetNoise, scaleNoise);

        for (int i = 0; i < values.buffer.length; i++) {
            // TODO: interpolate based on the density values
            values.buffer[i] += newVals[i];
        }

    }

    public static void sampleNoiseColumn(double[] buffer, int x, int z, OctavePerlinNoiseSampler caveNoise, OctavePerlinNoiseSampler offsetNoise, OctavePerlinNoiseSampler scaleNoise) {
        double offset = offsetNoise.sample(x / 128.0, 5423.434, z / 128.0) * 5.45;
        Random random = new Random(((long) x << 1) * 341873128712L + ((long) z << 1) * 132897987541L);

        // generate pillar
        if (random.nextInt(24) == 0) {
            // density: 4 is a stalactite/stalagmite, 10 is pillar
            offset += 4.0 + random.nextDouble() * 6;
        }

        for (int y = 0; y < 17; y++) {
            buffer[y] = sampleNoise(caveNoise, scaleNoise, x, y - 8, z) + getFalloff(offset, y - 8);
        }
    }

    private static double sampleNoise(OctavePerlinNoiseSampler caveNoise, OctavePerlinNoiseSampler scaleNoise, int x, int y, int z) {
        double noise = 0;
        double amplitude = 1;

        for (int i = 0; i < 6; i++) {
            PerlinNoiseSampler sampler = caveNoise.getOctave(i);

            noise += sampler.sample(x * 2.63 * amplitude, y * 12.18 * amplitude, z * 2.63 * amplitude) / amplitude;

            amplitude /= 2.0;
        }

        noise /= 1.25;

        double scale = (scaleNoise.getOctave(0).sample(x / 96.0, y / 96.0, z / 96.0, 0, 0) + 0.2) * 30;
        noise += Math.min(scale, 0);

        return noise;
    }

    private static double getFalloff(double offset, int y) {
        double falloffScale = 21.5 + offset;

        double falloff = Math.max((falloffScale / (9 + y)), 0); // lower bound
        falloff += Math.max((falloffScale / (8.2 - y)), 0); // upper bound

        double scaledY = y + 10.0;

        falloff = (1.5 * falloff) - (0.1 * scaledY * scaledY) - (-4.0 * y);

        return falloff;
    }
}
