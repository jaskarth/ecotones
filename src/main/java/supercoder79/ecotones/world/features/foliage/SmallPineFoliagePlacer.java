package supercoder79.ecotones.world.features.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;

import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Slightly altered PineFoliagePlacer that makes large trees with thin leaf structures.
 */
public class SmallPineFoliagePlacer extends PineFoliagePlacer {
    public static final Codec<SmallPineFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillFoliagePlacerFields(instance).and(IntProvider.createValidatingCodec(0, 16).fieldOf("height").forGetter((pineFoliagePlacer) -> {
            return pineFoliagePlacer.height;
        })).apply(instance, SmallPineFoliagePlacer::new);
    });

    private final IntProvider height;
    public SmallPineFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset, height);
        this.height = height;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        for(int k = offset; k >= offset - foliageHeight; --k) {
            // topmost is a single block
            if (k == offset) {
                this.generateSquare(world, replacer, random, config, treeNode.getCenter(), 0, k, treeNode.isGiantTrunk());
            } else {
                // everything after is a +
                this.generateSquare(world, replacer, random, config, treeNode.getCenter(), 1, k, treeNode.isGiantTrunk());
            }
        }
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return EcotonesFoliagePlacers.SMALL_PINE;
    }
}