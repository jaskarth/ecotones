package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class RockSpireFeatureConfig implements FeatureConfig {
    public static final Codec<RockSpireFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter(config -> config.provider)
    ).apply(instance, RockSpireFeatureConfig::new));

    public final BlockStateProvider provider;

    public RockSpireFeatureConfig(BlockStateProvider provider) {
        this.provider = provider;
    }
}
