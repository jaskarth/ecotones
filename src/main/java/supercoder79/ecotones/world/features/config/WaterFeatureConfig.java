package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public class WaterFeatureConfig implements FeatureConfig {
    public static final Codec<WaterFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.listOf().fieldOf("targets").forGetter(config -> config.targets)
    ).apply(instance, WaterFeatureConfig::new));

    public final List<BlockState> targets;

    public WaterFeatureConfig(List<BlockState> targets) {
        this.targets = targets;
    }
}
