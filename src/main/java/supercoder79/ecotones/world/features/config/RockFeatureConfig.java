package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;


public class RockFeatureConfig implements FeatureConfig {
    public static final Codec<RockFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter(config -> config.state),
            Codec.INT.fieldOf("start_radius").orElse(0).forGetter(config -> config.startRadius),
            Codec.BOOL.fieldOf("post_process").orElse(true).forGetter(config -> config.postProcess)
    ).apply(instance, RockFeatureConfig::new));

    public final BlockState state;
    public final int startRadius;
    public final boolean postProcess;

    public RockFeatureConfig(BlockState state, int startRadius) {
        this(state, startRadius, true);
    }

    public RockFeatureConfig(BlockState state, int startRadius, boolean postProcess) {
        this.state = state;
        this.startRadius = startRadius;
        this.postProcess = postProcess;
    }
}
