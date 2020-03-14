package supercoder79.ecotones.chunk;

import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

import java.util.stream.IntStream;

public class EcotonesChunkGenerator extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig> {
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (fs) -> {
        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float)(i * i + j * j) + 0.2F);
                fs[i + 2 + (j + 2) * 5] = f;
            }
        }

    });

    private final OctavePerlinNoiseSampler noiseSampler;

    public EcotonesChunkGenerator(IWorld world, BiomeSource biomeSource, OverworldChunkGeneratorConfig config) {
        super(world, biomeSource, 4, 8, 256, config, true);
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
    }

    @Override
    protected double[] computeNoiseRange(int x, int z) {
        double[] ds = new double[2];
        float f = 0.0F;
        float g = 0.0F;
        float h = 0.0F;
        int j = this.getSeaLevel();
        float k = this.biomeSource.getBiomeForNoiseGen(x, j, z).getDepth();

        for(int l = -2; l <= 2; ++l) {
            for(int m = -2; m <= 2; ++m) {
                Biome biome = this.biomeSource.getBiomeForNoiseGen(x + l, j, z + m);
                float n = biome.getDepth();
                float o = biome.getScale();

                float p = BIOME_WEIGHT_TABLE[l + 2 + (m + 2) * 5] / (n + 2.0F);
                if (biome.getDepth() > k) {
                    p /= 2.0F;
                }

                f += o * p;
                g += n * p;
                h += p;
            }
        }

        f /= h;
        g /= h;
        f = f * 0.9F + 0.1F;
        g = (g * 4.0F - 1.0F) / 8.0F;
        ds[0] = (double)g + this.sampleNoise(x, z);
        ds[1] = (double)f;
        return ds;
    }

    @Override
    protected double computeNoiseFalloff(double depth, double scale, int y) {
        double e = ((double)y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;
        if (e < 0.0D) {
            e *= 4.0D;
        }

        return e;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    public int getSpawnHeight() {
        return 6;
    }

    private double sampleNoise(int x, int y) {
        double d = this.noiseSampler.sample((double)(x * 200), 10.0D, (double)(y * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (d < 0.0D) {
            d = -d * 0.3D;
        }

        d = d * 3.0D - 2.0D;
        if (d < 0.0D) {
            d /= 28.0D;
        } else {
            if (d > 1.0D) {
                d = 1.0D;
            }

            d /= 40.0D;
        }

        return d;
    }
}
