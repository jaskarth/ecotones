package com.jaskarth.ecotones.world.worldgen.layers.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.ParentedLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.*;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;
import com.jaskarth.ecotones.world.worldgen.biome.BiomeHelper;
import com.jaskarth.ecotones.world.worldgen.layers.generation.ClimateLayer;
import com.jaskarth.ecotones.world.worldgen.layers.seed.SeedLayer;

import java.util.Random;

public enum ShorelineLayer implements ParentedLayer, IdentityCoordinateTransformer, SeedLayer {
    INSTANCE;
    public static final Identifier DRY = new Identifier("ecotones", "dry_beach");
    public static final Identifier TROPICAL = new Identifier("ecotones", "tropical_beach");
    public static final Identifier GRAVEL = new Identifier("ecotones", "gravel_beach");

    private OpenSimplexNoise beachNoise;

    private double beachOffsetX = 0;
    private double beachOffsetZ = 0;

    public int sample(LayerRandomnessSource context, int x, int z, int n, int e, int s, int w, int center) {
        if (BiomeRegistries.NO_BEACH_BIOMES.contains(Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(center)).get())) {
            return center;
        }

        if (!BiomeHelper.isOcean(center)) {
            if (BiomeHelper.isOcean(n) || BiomeHelper.isOcean(e) || BiomeHelper.isOcean(s) || BiomeHelper.isOcean(w)) {
                double humidity = MathHelper.clamp(ClimateLayer.humidityNoise.sample(((x >> 4) + ClimateLayer.INSTANCE.humidityOffsetX) / 2.5, ((z >> 4) + ClimateLayer.INSTANCE.humidityOffsetZ) / 2.5) * 1.25, -1, 1);
                if (humidity < -0.5) {
                    return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(DRY));
                } else {
                    // make gravel beaches sometimes
                    if (beachNoise.sample((x + beachOffsetX) / 24.0, (z + beachOffsetZ) / 24.0) > 0.5) {
                        return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(GRAVEL));
                    }

                    return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(TROPICAL));
                }
            }
        }

        return center;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        // We need the x/z coords in addition to the samples
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
        beachOffsetX = (random.nextDouble() - 0.5) * 10000;
        beachOffsetZ = (random.nextDouble() - 0.5) * 10000;
        beachNoise = new OpenSimplexNoise(seed - 120);
        return this.create(context, parent);
    }
}
