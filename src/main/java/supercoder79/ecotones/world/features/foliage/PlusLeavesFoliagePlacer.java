package supercoder79.ecotones.world.features.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Set;
import java.util.function.BiConsumer;

public class PlusLeavesFoliagePlacer extends FoliagePlacer {
    public static final Codec<PlusLeavesFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> FoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, PlusLeavesFoliagePlacer::new));

    public PlusLeavesFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return EcotonesFoliagePlacers.PLUS_LEAVES;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos pos = treeNode.getCenter();
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (Direction direction : Direction.values()) {
            mutable.set(pos).move(direction);
            if (TreeFeature.canReplace(world, mutable)) {
                placeFoliageBlock(world, placer, random, config, mutable);
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return false;
    }
}
