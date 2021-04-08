package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.util.Shapes;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LarchTreeFeature extends Feature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);

    public LarchTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        SimpleTreeFeatureConfig config = context.getConfig();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        int heightAddition = 3 + random.nextInt(4);

        double maxRadius = random.nextDouble() * 0.3 + 2.2;

        BlockPos.Mutable mutable = pos.mutableCopy();

        List<BlockPos> logs = new ArrayList<>();
        for (int y = 0; y < 8 + heightAddition; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            logs.add(mutable.toImmutable());
            mutable.move(Direction.UP);
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, 1 + heightAddition);

        List<BlockPos> leaves = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * model(y / 8.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                    leaves.add(leafPos.toImmutable());
                }
            });
            mutable.move(Direction.UP);
        }

        PINECONES.generate(world, random, logs, leaves, ImmutableSet.of(), new BlockBox());

        return true;
    }

    // Desmos: -x^{2}+0.4x+0.6
    private static double model(double x) {
        return (-(x * x) + (0.4 * x) + 0.6) * 1.6;
    }
}
