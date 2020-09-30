package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.technical.DryBeachBiome;
import supercoder79.ecotones.world.biome.technical.GravelBeachBiome;
import supercoder79.ecotones.world.biome.technical.TropicalBeachBiome;
import supercoder79.ecotones.world.layers.generation.ClimateLayers;
import supercoder79.ecotones.world.layers.seed.SeedLayer;

import java.util.Random;

public enum ShorelineLayer implements ParentedLayer, IdentityCoordinateTransformer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise beachNoise;

    private double offsetX = 0;
    private double offsetZ = 0;

    public int sample(LayerRandomnessSource context, int x, int z, int n, int e, int s, int w, int center) {
        if (BiomeRegistries.NO_BEACH_BIOMES.contains(center)) return center;

        if (!BiomeUtil.isOcean(center)) {
            if (BiomeUtil.isOcean(n) || BiomeUtil.isOcean(e) || BiomeUtil.isOcean(s) || BiomeUtil.isOcean(w)) {
                double humidity = MathHelper.clamp(ClimateLayers.humidityNoise.sample(((x >> 4) + ClimateLayers.INSTANCE.offsetX) / 2.5, ((z >> 4) + ClimateLayers.INSTANCE.offsetZ) / 2.5) * 1.25, -1, 1);
                if (humidity < -0.5) {
                    return Registry.BIOME.getRawId(DryBeachBiome.INSTANCE);
                } else {
                    // make gravel beaches sometimes
                    if (beachNoise.sample((x + offsetX) / 24.0, (z + offsetZ) / 24.0) > 0.5) {
                        return Registry.BIOME.getRawId(GravelBeachBiome.INSTANCE);
                    }

                    return Registry.BIOME.getRawId(TropicalBeachBiome.INSTANCE);
                }
            }
        }

        return center;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        return this.sample(context, x, z,
                parent.sample(x + 1, z),
                parent.sample(x, z + 1),
                parent.sample(x - 1, z),
                parent.sample(x, z - 1),
                parent.sample(x, z));
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        Random random = new Random(seed - 120);
        offsetX = (random.nextDouble()-0.5)*10000;
        offsetZ = (random.nextDouble()-0.5)*10000;
        beachNoise = new OpenSimplexNoise(seed - 120);
        return this.create(context, parent);
    }
}
