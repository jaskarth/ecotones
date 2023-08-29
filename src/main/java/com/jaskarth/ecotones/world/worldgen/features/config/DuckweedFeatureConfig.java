package com.jaskarth.ecotones.world.worldgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DuckweedFeatureConfig implements FeatureConfig {
    public static final Codec<DuckweedFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProvider.VALUE_CODEC.fieldOf("count").forGetter(c -> c.count),
            IntProvider.VALUE_CODEC.fieldOf("spread").forGetter(c -> c.spread)
    ).apply(instance, DuckweedFeatureConfig::new));

    public final IntProvider count;
    public final IntProvider spread;

    public DuckweedFeatureConfig(IntProvider count, IntProvider spread) {
        this.count = count;
        this.spread = spread;
    }
}
