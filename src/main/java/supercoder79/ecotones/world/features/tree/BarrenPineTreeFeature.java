package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.BoxHelper;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.DefaultDataHolder;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.tree.GeneratedTreeData;
import supercoder79.ecotones.world.tree.gen.BarrenTreeGenerator;
import supercoder79.ecotones.world.treedecorator.LeafPileTreeDecorator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class BarrenPineTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);

    private static final LeafPileTreeDecorator LEAF_PILES = new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 6, 3);

    public BarrenPineTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
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

        DataHolder data;
        if (generator instanceof DataHolder) {
            data = (DataHolder) generator;
        } else {
            data = DefaultDataHolder.INSTANCE;
        }

        GeneratedTreeData treeData = BarrenTreeGenerator.INSTANCE.generate(world, pos, random, data, config);

        BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

        TreeDecorator.Generator decorator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), new HashSet<>(), new HashSet<>(treeData.leafPositions), Set.of());

        PINECONES.generate(decorator);
        LEAF_PILES.generate(decorator);

        return true;
    }


}
