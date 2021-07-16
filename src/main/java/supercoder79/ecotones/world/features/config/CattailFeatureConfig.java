package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public class CattailFeatureConfig implements FeatureConfig {
    public static final Codec<CattailFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProvider.VALUE_CODEC.fieldOf("count").forGetter(c -> c.count),
            Codec.BOOL.fieldOf("needs_water").forGetter(c -> c.needsWater),
            IntProvider.VALUE_CODEC.fieldOf("spread_").forGetter(c -> c.spread)
    ).apply(instance, CattailFeatureConfig::new));

    public final IntProvider count;
    public final boolean needsWater;
    public final IntProvider spread;

    public CattailFeatureConfig(IntProvider count, boolean needsWater, IntProvider spread) {
        this.count = count;
        this.needsWater = needsWater;
        this.spread = spread;
    }
}
