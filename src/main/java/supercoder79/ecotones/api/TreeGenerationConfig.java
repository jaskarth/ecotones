package supercoder79.ecotones.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

/**
 * Generation details for an ecotones tree. Tree generators may not implement every detail here.
 */
public class TreeGenerationConfig implements FeatureConfig {
    public static final Codec<TreeGenerationConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.DOUBLE.fieldOf("target_count").forGetter(config -> config.targetCount),
            BlockState.CODEC.fieldOf("wood_state").forGetter(config -> config.woodState),
            BlockState.CODEC.fieldOf("leaf_state").forGetter(config -> config.leafState),
            Codec.INT.fieldOf("branching_factor").forGetter(config -> config.branchingFactor),
            Codec.INT.fieldOf("thick_trunk_depth").forGetter(config -> config.thickTrunkDepth),
            Codec.INT.fieldOf("min_size").forGetter(config -> config.minSize),
            Codec.INT.fieldOf("noise_coefficient").forGetter(config -> config.noiseCoefficient),
            Codec.DOUBLE.fieldOf("yaw_change").forGetter(config -> config.yawChange),
            Codec.DOUBLE.fieldOf("pitch_change").forGetter(config -> config.pitchChange),
            Codec.BOOL.fieldOf("generate_vines").forGetter(config -> config.generateVines),
            Codec.INT.fieldOf("trait_salt").forGetter(config -> config.traitSalt))
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
    public final int traitSalt;

    public TreeGenerationConfig(double targetCount, BlockState woodState, BlockState leafState, int branchingFactor, int thickTrunkDepth, int minSize, int noiseCoefficient, double yawChange, double pitchChange, boolean generateVines, int traitSalt) {
        this.generateVines = generateVines;
        this.decorationData = new DecorationData(targetCount, minSize, noiseCoefficient, false);
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
        this.traitSalt = traitSalt;
    }

    public static class DecorationData implements DecoratorConfig {
        public static final Codec<DecorationData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.DOUBLE.fieldOf("target_count").forGetter(config -> config.targetCount),
                Codec.INT.fieldOf("min_size").forGetter(config -> config.minSize),
                Codec.INT.fieldOf("noise_coefficient").forGetter(config -> config.noiseCoefficient),
                Codec.BOOL.fieldOf("ignore_ground_check").forGetter(config -> config.ignoreGroundCheck))
                .apply(instance, DecorationData::new));

        public final double targetCount;
        public final int minSize;
        public final int noiseCoefficient;
        public final boolean ignoreGroundCheck;

        public DecorationData(double targetCount, int minSize, int noiseCoefficient, boolean ignoreGroundCheck) {
            this.targetCount = targetCount;
            this.minSize = minSize;
            this.noiseCoefficient = noiseCoefficient;
            this.ignoreGroundCheck = ignoreGroundCheck;
        }
    }
}
