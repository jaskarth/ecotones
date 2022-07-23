package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.BoxHelper;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.treedecorator.LeafPileTreeDecorator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.*;
import java.util.function.BiConsumer;

public class TallPineTree extends EcotonesFeature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);
    private static final LeafPileTreeDecorator LEAF_PILES = new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 8, 3);

    public TallPineTree(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        List<BlockPos> leaves = new ArrayList<>();
        int height = 10 + random.nextInt(8);
        double leafStart = 0.2 + random.nextDouble() * 0.55;
        // Needs to be odd trunk size for this to work
        if (height % 2 == 0) {
            height++;
        }

        for (int y = 0; y < height; y++) {
            world.setBlockState(pos.up(y), config.woodState, 3);

            double progress = (double) y / height;

            if (progress >= leafStart) {
                if (y % 2 == 0) {
                    generateLeafLayer(pos, y, y > height - 3, random, leaves, context);
                } else {
                    generateSmallLeafLayer(pos, y, y > height - 3, random, leaves, context);
                }
            }
        }

        generateSmallLeafLayer(pos, height, true, random, leaves, context);
        FeatureHelper.placeLeaves(context, pos.up(height + 1));
        leaves.add(pos.up(height + 1));

        BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

        TreeDecorator.Generator decorator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), new HashSet<>(), new HashSet<>(leaves), Set.of());

        PINECONES.generate(decorator);
        LEAF_PILES.generate(decorator);

        return false;
    }

    private static void generateLeafLayer(BlockPos pos, int y, boolean force, Random random, List<BlockPos> leaves, FeatureContext<SimpleTreeFeatureConfig> context) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos local = pos.add(x, y, z);

                int manhattan = Math.abs(x) + Math.abs(z);

                if (manhattan <= 1) {
                    FeatureHelper.placeLeaves(context, local);
                    leaves.add(local);
                } else if (manhattan == 2) {
                    if (random.nextDouble() < 0.8 || force) {
                        FeatureHelper.placeLeaves(context, local);
                        leaves.add(local);
                    }
                }
            }
        }
    }

    private static void generateSmallLeafLayer(BlockPos pos, int y, boolean force, Random random, List<BlockPos> leaves, FeatureContext<SimpleTreeFeatureConfig> context) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (Math.abs(x) != 1 || Math.abs(z) != 1) {
                    BlockPos local = pos.add(x, y, z);

                    if (random.nextDouble() < 0.8 || force) {
                        FeatureHelper.placeLeaves(context, local);
                        leaves.add(local);
                    }
                }
            }
        }
    }
}
