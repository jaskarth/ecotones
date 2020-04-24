package supercoder79.ecotones.api;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

/**
 * Generation details for an ecotones tree. Tree generators may not implement every detail here.
 */
public class TreeGenerationConfig implements FeatureConfig, DecoratorConfig {
    public final double targetCount;
    public final BlockState woodState;
    public final BlockState leafState;
    public final int branchingFactor;
    public final int thickTrunkDepth;
    public final int minSize;
    public final int noiseCoefficient;
    public final double yawChange;
    public final double pitchChange;

    public TreeGenerationConfig(double targetCount, BlockState woodState, BlockState leafState, int branchingFactor, int thickTrunkDepth, int minSize, int noiseCoefficient, double yawChange, double pitchChange) {
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

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return null;
    }

    //this is highly blessed but it's alright
    public static TreeGenerationConfig deserialize(Dynamic<?> dynamic) {
        return TreeType.OAK.config;
    }
}
