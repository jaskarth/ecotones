package supercoder79.ecotones.chunk;

import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import supercoder79.ecotones.biome.EcotonesBiome;
import supercoder79.ecotones.noise.OctaveNoiseSampler;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.stream.IntStream;

public class EcotonesChunkGenerator extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig> {
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (table) -> {
        for(int x = -2; x <= 2; ++x) {
            for(int z = -2; z <= 2; ++z) {
                float weight = 10.0F / MathHelper.sqrt((float)(x * x + z * z) + 0.2F);
                table[x + 2 + (z + 2) * 5] = weight;
            }
        }
    });

    private final OctavePerlinNoiseSampler hillinessNoise;

    private final OctavePerlinNoiseSampler noiseSampler1;
    private final OctavePerlinNoiseSampler noiseSampler2;
    private final OctavePerlinNoiseSampler lowerNoise;

    private final OctaveNoiseSampler<OpenSimplexNoise> scaleNoise;

    public EcotonesChunkGenerator(IWorld world, BiomeSource biomeSource, OverworldChunkGeneratorConfig config) {
        super(world, biomeSource, 4, 8, 256, config, true);
        this.hillinessNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.noiseSampler1 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.noiseSampler2 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.lowerNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));

        this.scaleNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 8, 256, 0.2, -0.2);
    }

    @Override
    protected double[] computeNoiseRange(int x, int z) {
        double[] buffer = new double[3];
        float weightedScale = 0.0F;
        float weightedDepth = 0.0F;
        double weightedHilliness = 0.0F;
        double weightedVolatility = 0.0F;
        float weights = 0.0F;
        int seaLevel = this.getSeaLevel();
        float centerDepth = this.biomeSource.getBiomeForNoiseGen(x, seaLevel, z).getDepth();

        for(int l = -2; l <= 2; ++l) {
            for(int m = -2; m <= 2; ++m) {
                Biome biome = this.biomeSource.getBiomeForNoiseGen(x + l, seaLevel, z + m);
                //vanilla attributes
                float depth = biome.getDepth();
                float scale = biome.getScale();

                //hilliness and volatility
                double hilliness = 1;
                double volatility = 1;
                if (biome instanceof EcotonesBiome) {
                    hilliness = ((EcotonesBiome)biome).getHilliness();
                    volatility = ((EcotonesBiome)biome).getVolatility();
                }

                float weight = BIOME_WEIGHT_TABLE[l + 2 + (m + 2) * 5] / (depth + 2.0F);
                if (biome.getDepth() > centerDepth) {
                    weight /= 2.0F;
                }

                weightedScale += scale * weight;
                weightedDepth += depth * weight;
                weightedHilliness += hilliness * weight;
                weightedVolatility += volatility * weight;
                weights += weight;
            }
        }

        weightedScale /= weights;
        weightedDepth /= weights;
        weightedHilliness /= weights;
        weightedVolatility /= weights;

        weightedScale = weightedScale * 0.9F + 0.1F;
        weightedDepth = (weightedDepth * 4.0F - 1.0F) / 8.0F;
        buffer[0] = (double)weightedDepth + (this.sampleNoise(x, z) * weightedHilliness);
        buffer[1] = weightedScale + scaleNoise.sample(x, z);
        buffer[2] = weightedVolatility;
        return buffer;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z, double d, double e, double f, double g, int i, int j) {
        double[] noiseRange = this.computeNoiseRange(x, z);
        double depth = noiseRange[0];
        double scale = noiseRange[1];
        double volatility = noiseRange[2];

        //magic shit that i have no idea about how it works
        double delta1 = this.method_16409();
        double delta2 = this.method_16410();

        for(int y = 0; y < this.getNoiseSizeY(); ++y) {
            double noise = this.sampleNoise(x, y, z, d, e, f, g) + scaleNoise.sample(x, y, z);
            //calculate volatility
            noise -= this.computeNoiseFalloff(depth, scale, y) * volatility;
            if ((double)y > delta1) {
                noise = MathHelper.clampedLerp(noise, (double)j, ((double)y - delta1) / (double)i);
            } else if ((double)y < delta2) {
                noise = MathHelper.clampedLerp(noise, -30.0D, (delta2 - (double)y) / (delta2 - 1.0D));
            }

            buffer[y] = noise;
        }

    }

    //noise magic
    private double sampleNoise(int x, int y, int z, double d, double e, double f, double g) {
        double h = 0.0D;
        double i = 0.0D;
        double j = 0.0D;
        double k = 1.0D;

        for(int l = 0; l < 16; ++l) {
            double m = OctavePerlinNoiseSampler.maintainPrecision((double)x * d * k);
            double n = OctavePerlinNoiseSampler.maintainPrecision((double)y * e * k);
            double o = OctavePerlinNoiseSampler.maintainPrecision((double)z * d * k);
            double p = e * k;
            PerlinNoiseSampler perlinNoiseSampler = this.noiseSampler1.getOctave(l);
            if (perlinNoiseSampler != null) {
                h += perlinNoiseSampler.sample(m, n, o, p, (double)y * p) / k;
            }

            PerlinNoiseSampler perlinNoiseSampler2 = this.noiseSampler2.getOctave(l);
            if (perlinNoiseSampler2 != null) {
                i += perlinNoiseSampler2.sample(m, n, o, p, (double)y * p) / k;
            }

            if (l < 8) {
                PerlinNoiseSampler perlinNoiseSampler3 = this.lowerNoise.getOctave(l);
                if (perlinNoiseSampler3 != null) {
                    j += perlinNoiseSampler3.sample(OctavePerlinNoiseSampler.maintainPrecision((double)x * f * k), OctavePerlinNoiseSampler.maintainPrecision((double)y * g * k), OctavePerlinNoiseSampler.maintainPrecision((double)z * f * k), g * k, (double)y * g * k) / k;
                }
            }

            k /= 2.0D;
        }

        return MathHelper.clampedLerp(h / 512.0D, i / 512.0D, (j / 10.0D + 1.0D) / 2.0D);
    }

    @Override
    protected double computeNoiseFalloff(double depth, double scale, int y) {
        double falloff = ((double)y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;
        if (falloff < 0.0D) {
            falloff *= 4.0D;
        }

        return falloff;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    public int getSpawnHeight() {
        return 63;
    }

    //height additions - makes the terrain a bit hillier
    private double sampleNoise(int x, int y) {
        double noise = this.hillinessNoise.sample((double)(x * 200), 10.0D, (double)(y * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (noise < 0.0D) {
            noise = -noise * 0.3D;
        }

        noise = noise * 3.0D - 2.0D;
        if (noise < 0.0D) {
            noise /= 28.0D;
        } else {
            if (noise > 1.0D) {
                noise = 1.0D;
            }

            noise /= 40.0D;
        }

        return noise;
    }
}
