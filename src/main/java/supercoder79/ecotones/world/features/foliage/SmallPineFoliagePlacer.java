package supercoder79.ecotones.world.features.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;

import java.util.Random;
import java.util.Set;

/**
 * Slightly altered PineFoliagePlacer that makes large trees with thin leaf structures.
 */
public class SmallPineFoliagePlacer extends PineFoliagePlacer {
    public static final Codec<SmallPineFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillFoliagePlacerFields(instance).and(UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("height").forGetter((pineFoliagePlacer) -> {
            return pineFoliagePlacer.height;
        })).apply(instance, SmallPineFoliagePlacer::new);
    });

    private final UniformIntDistribution height;
    public SmallPineFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, UniformIntDistribution height) {
        super(radius, offset, height);
        this.height = height;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int i, BlockBox blockBox) {
        for(int k = i; k >= i - foliageHeight; --k) {
            // topmost is a single block
            if (k == i) {
                this.generateSquare(world, random, config, treeNode.getCenter(), 0, leaves, k, treeNode.isGiantTrunk(), blockBox);
            } else {
                // everything after is a +
                this.generateSquare(world, random, config, treeNode.getCenter(), 1, leaves, k, treeNode.isGiantTrunk(), blockBox);
            }
        }
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return EcotonesFoliagePlacers.SMALL_PINE;
    }
}