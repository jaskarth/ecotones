package supercoder79.ecotones.features.config;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.class_5204;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.treedecorator.PineconeTreeDecorator;
import supercoder79.ecotones.util.DoubleOrNormalPlacer;

public class FeatureConfigHolder {

    // grass
    public static RandomPatchFeatureConfig REEDS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.reeds.getDefaultState(), 1)
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 3), new SimpleBlockPlacer()).tries(8).build();

    public static RandomPatchFeatureConfig MOSTLY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 8)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 6)
                    .addState(EcotonesBlocks.smallShrubBlock.getDefaultState(), 2)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 2)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig RARELY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig PRAIRIE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 16)
                    .addState(EcotonesBlocks.wildflowersBlock.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 2), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig TALL_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.shortGrass.getDefaultState(), 1)
                    .addState(EcotonesBlocks.wildflowersBlock.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 1), new DoubleOrNormalPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig DESERT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.sandyGrassBlock.getDefaultState(), 1), new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(24).build();

    // flowers
    public static RandomPatchFeatureConfig TAIGA_FLOWERS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.wildflowersBlock.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(64).build();

    // misc
    public static RandomPatchFeatureConfig SURFACE_ROCKS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.surfaceRockBlock.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(4).build();

    public static RandomPatchFeatureConfig CLOVER =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.cloverBlock.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(12).build();

    // trees
    public static TreeFeatureConfig SPRUCE_TREE_CONFIG =
            (new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
                    new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1),
                    new StraightTrunkPlacer(6, 4, 3),
                    new class_5204(2, 0, 2)))
                    .method_27374().build().method_27373(ImmutableList.of(new PineconeTreeDecorator(6)));
}
