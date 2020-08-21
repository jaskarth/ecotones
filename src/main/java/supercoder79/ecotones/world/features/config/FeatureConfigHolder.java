package supercoder79.ecotones.world.features.config;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placer.ColumnPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.DoubleOrNormalPlacer;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.OptionalInt;

public class FeatureConfigHolder {

    // grass
    public static RandomPatchFeatureConfig REEDS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.REEDS.getDefaultState(), 1)
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 3), new SimpleBlockPlacer()).tries(16).build();

    public static RandomPatchFeatureConfig MOSTLY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 8)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 6)
                    .addState(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 2)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();
    public static RandomPatchFeatureConfig COOL_SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 4)
                    .addState(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 5)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(48).build();

    public static RandomPatchFeatureConfig SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 2)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig RARELY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig PRAIRIE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 16)
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 2), new SimpleBlockPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig GRASSLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 1), new DoubleOrNormalPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig TALL_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 6)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 3), new DoubleOrNormalPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig BLUEBELL_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.BLUEBELL.getDefaultState(), 32)
                    .addState(Blocks.GRASS.getDefaultState(), 4)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 2), new DoubleOrNormalPlacer()).tries(32).build();

    public static RandomPatchFeatureConfig DESERT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SANDY_GRASS.getDefaultState(), 1), new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(24).build();

    // flowers
    public static RandomPatchFeatureConfig TAIGA_FLOWERS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(64).build();

    public static RandomPatchFeatureConfig WIDE_FERNS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.WIDE_FERN.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(11)
                    .spreadZ(11)
                    .tries(48).build();

    public static RandomPatchFeatureConfig SMALL_LILAC =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SMALL_LILAC.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(12).build();

    public static RandomPatchFeatureConfig CYAN_ROSE =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.CYAN_ROSE.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(32).build();

    // misc
    public static RandomPatchFeatureConfig SURFACE_ROCKS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SURFACE_ROCK.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(4).build();

    public static RandomPatchFeatureConfig CLOVER =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.CLOVER.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(12).build();

    public static RandomPatchFeatureConfig LARGE_CACTUS_PATCH =
            new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.CACTUS.getDefaultState()),
                    new ColumnPlacer(1, 2))
                    .tries(32).cannotProject().build();

    // trees
    public static TreeFeatureConfig SPRUCE_TREE_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
                    new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1),
                    new StraightTrunkPlacer(6, 4, 3),
                    new TwoLayersFeatureSize(2, 0, 2))
                    .ignoreVines().build().setTreeDecorators(ImmutableList.of(new PineconeTreeDecorator(6)));

    public static TreeFeatureConfig DEAD_LARGE_OAK =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
                    new LargeOakFoliagePlacer(2, 0, 4, 0, 4),
                    new LargeOakTrunkPlacer(8, 5, 0),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                    .ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build();
}
