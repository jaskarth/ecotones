package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DuckweedFeatureConfig implements FeatureConfig {
    public static final Codec<DuckweedFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UniformIntDistribution.CODEC.fieldOf("count").forGetter(c -> c.count),
            UniformIntDistribution.CODEC.fieldOf("spread").forGetter(c -> c.spread)
    ).apply(instance, DuckweedFeatureConfig::new));

    public final UniformIntDistribution count;
    public final UniformIntDistribution spread;

    public DuckweedFeatureConfig(UniformIntDistribution count, UniformIntDistribution spread) {
        this.count = count;
        this.spread = spread;
    }
}
