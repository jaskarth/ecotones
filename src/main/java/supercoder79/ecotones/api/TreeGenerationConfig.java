package supercoder79.ecotones.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.TreeDecorator;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.TrunkPlacer;

/**
 * Generation details for an ecotones tree. Tree generators may not implement every detail here.
 */
public class TreeGenerationConfig implements FeatureConfig {
    public static final Codec<TreeGenerationConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.DOUBLE.fieldOf("target_count").forGetter((config) -> config.targetCount),
            BlockState.CODEC.fieldOf("wood_state").forGetter((config) -> config.woodState),
            BlockState.CODEC.fieldOf("leaf_state").forGetter((config) -> config.leafState),
            Codec.INT.fieldOf("branching_factor").forGetter((config) -> config.branchingFactor),
            Codec.INT.fieldOf("thick_trunk_depth").forGetter((config) -> config.thickTrunkDepth),
            Codec.INT.fieldOf("min_size").forGetter((config) -> config.minSize),
            Codec.INT.fieldOf("noise_coefficient").forGetter((config) -> config.noiseCoefficient),
            Codec.DOUBLE.fieldOf("yaw_change").forGetter((config) -> config.yawChange),
            Codec.DOUBLE.fieldOf("pitch_change").forGetter((config) -> config.pitchChange),
            Codec.BOOL.fieldOf("generate_vines").forGetter((config) -> config.generateVines))
            .apply(instance, TreeGenerationConfig::new));
    public final DecorationData decorationData;
    public final double targetCount;
    public final BlockState woodState;
    public final BlockState leafState;
    public final int branchingFactor;
    public final int thickTrunkDepth;
    public final int minSize;
    public final int noiseCoefficient;
    public final double yawChange;
    public final double pitchChange;
    public final boolean generateVines;

    public TreeGenerationConfig(double targetCount, BlockState woodState, BlockState leafState, int branchingFactor, int thickTrunkDepth, int minSize, int noiseCoefficient, double yawChange, double pitchChange, boolean generateVines) {
        this.generateVines = generateVines;
        this.decorationData = new DecorationData(targetCount, minSize, noiseCoefficient);
        this.targetCount = targetCount;
        this.woodState = woodState;
        if (leafState.getProperties().contains(Properties.DISTANCE_1_7)) {
            this.leafState = leafState.with(Properties.DISTANCE_1_7, 1);
        } else {
            this.leafState = leafState;
        }
        this.branchingFactor = branchingFactor;
        this.thickTrunkDepth = thickTrunkDepth;
        this.minSize = minSize;
        this.noiseCoefficient = noiseCoefficient;
        this.yawChange = yawChange;
        this.pitchChange = pitchChange;
    }

    public static class DecorationData implements DecoratorConfig {
        public static final Codec<DecorationData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.DOUBLE.fieldOf("target_count").forGetter((config) -> config.targetCount),
                Codec.INT.fieldOf("min_size").forGetter((config) -> config.minSize),
                Codec.INT.fieldOf("noise_coefficient").forGetter((config) -> config.noiseCoefficient))
                .apply(instance, DecorationData::new));

        public final double targetCount;
        public final int minSize;
        public final int noiseCoefficient;

        public DecorationData(double targetCount, int minSize, int noiseCoefficient) {
            this.targetCount = targetCount;
            this.minSize = minSize;
            this.noiseCoefficient = noiseCoefficient;
        }
    }
}
