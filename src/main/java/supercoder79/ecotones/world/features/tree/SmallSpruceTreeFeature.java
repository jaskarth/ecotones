package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.tree.SmallSpruceTrait;
import supercoder79.ecotones.tree.Traits;
import supercoder79.ecotones.tree.smallspruce.DefaultSmallSpruceTrait;
import supercoder79.ecotones.util.Shapes;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.treedecorator.LichenTreeDecorator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmallSpruceTreeFeature extends Feature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);
    private static final LichenTreeDecorator LICHEN = new LichenTreeDecorator(3);

    public SmallSpruceTreeFeature() {
        super(SimpleTreeFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        SimpleTreeFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        // Trait data
        SmallSpruceTrait trait = DefaultSmallSpruceTrait.INSTANCE;
        if (generator instanceof EcotonesChunkGenerator) {
            long traits = ((EcotonesChunkGenerator) generator).getTraits(pos.getX() >> 4, pos.getZ() >> 4, TreeType.SMALL_SPRUCE_SALT);
            trait = Traits.get(Traits.SMALL_SPRUCE, traits);
        }

        int heightAddition = trait.extraHeight(random);

        double maxRadius = trait.maxRadius(random);

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
        for (int y = 0; y < 9; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * trait.model(y / 10.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                    leaves.add(leafPos.toImmutable());
                }
            });
            mutable.move(Direction.UP);
        }

        // Generate pinecones
        PINECONES.generate(world, random, logs, leaves, ImmutableSet.of(), new BlockBox());

        // Generate lichen
        LICHEN.generate(world, random, logs, leaves, ImmutableSet.of(), new BlockBox());

        return false;
    }
}
