package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.world.gen.feature.FeatureConfig;

public class SimpleTreeFeatureConfig implements FeatureConfig {
    public static final Codec<SimpleTreeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockState.CODEC.fieldOf("wood_state").forGetter((config) -> config.woodState),
            BlockState.CODEC.fieldOf("leaf_state").forGetter((config) -> config.leafState))
            .apply(instance, SimpleTreeFeatureConfig::new));

    public final BlockState woodState;
    public final BlockState leafState;

    public SimpleTreeFeatureConfig(BlockState woodState, BlockState leafState) {
        this.woodState = woodState;

        if (leafState.getProperties().contains(Properties.DISTANCE_1_7)) {
            this.leafState = leafState.with(Properties.DISTANCE_1_7, 1);
        } else {
            this.leafState = leafState;
        }
    }
}
