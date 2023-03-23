package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.util.Shapes;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.Random;

public class BigShrubFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    public BigShrubFeature(Codec<SimpleTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        double maxRadius = 1.75 + ((random.nextDouble() - 0.5) * 0.2);

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int y = 0; y < 4; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            mutable.move(Direction.UP);
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, 1);

        for (int y = 0; y < 5; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * radius(y / 5.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                }
            });
            mutable.move(Direction.UP);
        }

        return false;
    }

    private double radius(double x) {
        //TODO: refactor to not use a 4th degree polynomial
        return (-2.7 * (x * x * x * x) + (1.95 * x) + 0.7);
    }
}
