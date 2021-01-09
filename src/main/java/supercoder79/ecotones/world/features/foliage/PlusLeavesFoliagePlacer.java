package supercoder79.ecotones.world.features.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class PlusLeavesFoliagePlacer extends FoliagePlacer {
    public static final Codec<PlusLeavesFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> FoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, PlusLeavesFoliagePlacer::new));

    public PlusLeavesFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return EcotonesFoliagePlacers.PLUS_LEAVES;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int i, BlockBox blockBox) {
        BlockPos pos = treeNode.getCenter();
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (Direction direction : Direction.values()) {
            mutable.set(pos).move(direction);
            if (TreeFeature.canReplace(world, mutable)) {
                world.setBlockState(mutable, config.leavesProvider.getBlockState(random, pos), 3);
                leaves.add(mutable.toImmutable());
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
