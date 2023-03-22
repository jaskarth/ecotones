package supercoder79.ecotones.world.gen.caves;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import supercoder79.ecotones.util.noise.OctaveNoise;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.gen.NoiseColumn;

import java.util.Random;
import java.util.stream.IntStream;

public class EcotonesCaveGenerator implements NoiseCaveGenerator {
    private OctavePerlinNoiseSampler caveNoise;
    private OctaveNoise offsetNoise;

    @Override
    public void init(long seed) {
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(seed));
        this.caveNoise = OctavePerlinNoiseSampler.create(chunkRandom, IntStream.rangeClosed(-5, 0));
        this.offsetNoise = new OctaveNoise(2, new Random(chunkRandom.nextLong()), 48, 48, 1.0, 2.0, 2.0);
    }

    @Override
    public void genColumn(int x, int z, NoiseColumn column) {
        double[] values = column.buffer;
        int maxY = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < 0) {
                maxY = i;
                break;
            }
        }
        if (maxY > 11) {
            maxY = 11;
        }

        double offset =  Math.max(0, Math.abs(offsetNoise.sample(x, z) * 4) - 1);
        Random random = new Random(((long) x << 1) * 341873128712L + ((long) z << 1) * 132897987541L);

        // generate pillar
        if (random.nextInt(24) == 0) {
            // density: 2 is a stalactite/stalagmite, 12 is pillar
            offset += 2.0 + random.nextDouble() * 10;
        }

        for (int i = 0; i < maxY - 3; i++) {
            double sample = caveNoise.sample(x * 2.31, i * 8.7, z * 2.31);
            sample += getFalloff(offset, i - 8, maxY - 8 - 3);
            values[i] = MathHelper.clampedMap(sample, -1, 0, -200, 200);
        }

        column.maxY = (maxY - 8) - 2;
    }

    // Desmos: y=\left(\max\left(\frac{1+o}{m-x},0\right)+\max\left(\frac{1+o}{9+x},0\right)\right)-\left(-0.05\cdot\left(x-5\right)\right)
    private static double getFalloff(double offset, int y, int maxY) {
        double falloffScale = 1.0 + offset;

        double falloff = Math.max((falloffScale / (9 + y)), 0); // lower bound
        falloff += Math.max((falloffScale / ((maxY + 0.2) - y)), 0); // upper bound

        double scaledY = y - 10.0;

        falloff += 0.05 * scaledY;

        return (falloff * 0.9);
    }
}
