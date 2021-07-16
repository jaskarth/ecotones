package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PatchFeatureConfig implements FeatureConfig {
    public static final Codec<PatchFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter(config -> config.state),
            Identifier.CODEC.fieldOf("target").forGetter(config -> Registry.BLOCK.getId(config.target)),
            IntProvider.VALUE_CODEC.fieldOf("radius").forGetter(config -> config.radius)
    ).apply(instance, PatchFeatureConfig::new));

    public final BlockState state;
    public final Block target;
    public final IntProvider radius;

    private PatchFeatureConfig(BlockState state, Identifier target, IntProvider radius) {
        this(state, Registry.BLOCK.get(target), radius);
    }

    public PatchFeatureConfig(BlockState state, Block target, IntProvider radius) {
        this.state = state;
        this.target = target;
        this.radius = radius;
    }
}
