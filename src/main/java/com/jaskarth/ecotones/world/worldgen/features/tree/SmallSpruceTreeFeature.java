package com.jaskarth.ecotones.world.worldgen.features.tree;

import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeature;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesChunkGenerator;
import com.jaskarth.ecotones.world.worldgen.tree.trait.EcotonesTreeTraits;
import com.jaskarth.ecotones.world.worldgen.tree.trait.smallspruce.SmallSpruceTrait;
import com.jaskarth.ecotones.world.worldgen.tree.trait.smallspruce.DefaultSmallSpruceTrait;
import com.jaskarth.ecotones.world.worldgen.tree.decorator.LeafPileTreeDecorator;
import com.jaskarth.ecotones.world.worldgen.tree.decorator.LichenTreeDecorator;
import com.jaskarth.ecotones.world.worldgen.tree.decorator.PineconeTreeDecorator;

import java.util.*;
import java.util.function.BiConsumer;

public class SmallSpruceTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);
    private static final LichenTreeDecorator LICHEN = new LichenTreeDecorator(3);
    private static final LeafPileTreeDecorator LEAF_PILES = new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 8, 3);

    public SmallSpruceTreeFeature() {
        super(SimpleTreeFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        // Trait data
        SmallSpruceTrait trait = DefaultSmallSpruceTrait.INSTANCE;
        if (generator instanceof EcotonesChunkGenerator) {
            trait = EcotonesTreeTraits.SMALL_SPRUCE.get((EcotonesChunkGenerator) generator, pos);
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
            FeatureHelper.circle(mutable.mutableCopy(), maxRadius * trait.model(y / 10.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                    leaves.add(leafPos.toImmutable());
                }
            });
            mutable.move(Direction.UP);
        }

        BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

        TreeDecorator.Generator decorator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), new HashSet<>(logs), new HashSet<>(leaves), Set.of());

        // Generate pinecones
        PINECONES.generate(decorator);

        // Generate lichen
        LICHEN.generate(decorator);

        // Generate leaf piles
        LEAF_PILES.generate(decorator);

        return false;
    }
}
