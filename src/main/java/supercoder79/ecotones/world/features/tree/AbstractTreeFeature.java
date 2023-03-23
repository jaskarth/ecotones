package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import supercoder79.ecotones.world.features.EcotonesFeature;

import java.util.*;
import java.util.function.BiConsumer;

@Deprecated
public abstract class AbstractTreeFeature<T extends TreeFeatureConfig> extends EcotonesFeature<T> {
    public AbstractTreeFeature(Codec<T> codec) {
        super(codec);
    }

    protected static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return state.isAir() || state.isIn(BlockTags.LEAVES) || isSoil(state) || state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.SAPLINGS) || block == Blocks.VINE;
        });
    }

    public static boolean isAir(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, AbstractBlockState::isAir);
    }

    protected static boolean isNaturalDirt(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return isSoil(state) && block != Blocks.GRASS_BLOCK && block != Blocks.MYCELIUM;
        });
    }

    protected static boolean isVine(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.getBlock() == Blocks.VINE);
    }

    public static boolean isWater(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.getBlock() == Blocks.WATER);
    }

    public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
    }

    public static boolean isNaturalDirtOrGrass(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, Feature::isSoil);
    }

    protected static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return isSoil(state) || block == Blocks.FARMLAND;
        });
    }

    public static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Material material = state.getMaterial();
            return material == Material.REPLACEABLE_PLANT;
        });
    }

    protected void setToDirt(ModifiableTestableWorld world, BlockPos pos) {
        if (!isNaturalDirt(world, pos)) {
            this.setBlockState(world, pos, Blocks.DIRT.getDefaultState());
        }

    }

    public static boolean setLogBlockState(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> trunkPositions, BlockBox box, TreeFeatureConfig config) {
        if (!isAirOrLeaves(world, pos) && !isReplaceablePlant(world, pos) && !isWater(world, pos)) {
            return false;
        } else {
            setBlockState(world, pos, config.trunkProvider.getBlockState(new CheckedRandom(random.nextLong()), pos), box);
            trunkPositions.add(pos.toImmutable());
            return true;
        }
    }

    protected boolean setLeavesBlockState(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
        if (!isAirOrLeaves(world, pos) && !isReplaceablePlant(world, pos) && !isWater(world, pos)) {
            return false;
        } else {
            setBlockState(world, pos, config.foliageProvider.getBlockState(new CheckedRandom(random.nextLong()), pos), box);
            leavesPositions.add(pos.toImmutable());
            return true;
        }
    }

    protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
        setBlockStateWithoutUpdatingNeighbors(world, pos, state);
    }

    protected static final void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state, BlockBox box) {
        setBlockStateWithoutUpdatingNeighbors(world, pos, state);
        box.encompass(new BlockBox(pos));
    }

    private static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, 19);
    }

    @Override
    public boolean generate(FeatureContext<T> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        T config = context.getConfig();

        Set<BlockPos> set = Sets.newHashSet();
        Set<BlockPos> set2 = Sets.newHashSet();
        Set<BlockPos> set3 = Sets.newHashSet();
        BlockBox blockBox = new BlockBox(0, 0, 0, 0, 0, 0);
        boolean bl = this.generate(world, random, pos, set, set2, blockBox, config);
        if (blockBox.getMinX() <= blockBox.getMaxX() && bl && !set.isEmpty()) {
            if (!config.decorators.isEmpty()) {
                List<BlockPos> list = Lists.newArrayList(set);
                List<BlockPos> list2 = Lists.newArrayList(set2);
                list.sort(Comparator.comparingInt(Vec3i::getY));
                list2.sort(Comparator.comparingInt(Vec3i::getY));

                BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

                TreeDecorator.Generator generator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), set, set2, set3);

                config.decorators.forEach((decorator) -> {
                    decorator.generate(generator);
                });
            }

            this.placeLogsAndLeaves(world, blockBox, random, set, set2, config);
            return true;
        } else {
            return false;
        }
    }

    private void placeLogsAndLeaves(WorldAccess world, BlockBox box, Random random, Set<BlockPos> logs, Set<BlockPos> leaves, T config) {
        for (BlockPos log : logs) {
            world.setBlockState(log, config.trunkProvider.getBlockState(new CheckedRandom(random.nextLong()), log), 3);
        }

        for (BlockPos leaf : leaves) {
            BlockState leafState = config.foliageProvider.getBlockState(new CheckedRandom(random.nextLong()), leaf);

            if (leafState.getProperties().contains(Properties.DISTANCE_1_7)) {
                leafState = leafState.with(Properties.DISTANCE_1_7, 1);
            }

            world.setBlockState(leaf, leafState, 3);
        }
    }

    protected abstract boolean generate(StructureWorldAccess world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, T config);
}