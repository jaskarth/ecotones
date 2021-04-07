package supercoder79.ecotones.world.features.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placer.ColumnPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.tree.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.tree.LeavesVineTreeDecorator;
import net.minecraft.world.gen.tree.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.DoubleOrNormalPlacer;
import supercoder79.ecotones.world.features.foliage.PlusLeavesFoliagePlacer;
import supercoder79.ecotones.world.features.foliage.SmallPineFoliagePlacer;
import supercoder79.ecotones.world.treedecorator.LeafPileTreeDecorator;
import supercoder79.ecotones.world.treedecorator.LichenTreeDecorator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.Optional;
import java.util.OptionalInt;

public final class FeatureConfigHolder {
    // grass
    public static final RandomPatchFeatureConfig REEDS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.REEDS.getDefaultState(), 1)
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 3), new SimpleBlockPlacer()).tries(16).build();

    public static final RandomPatchFeatureConfig MOSTLY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 8)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 6)
                    .addState(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 2)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig COOL_SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 4)
                    .addState(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 5)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(48).build();

    public static final RandomPatchFeatureConfig SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 2)
                    .addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig RARELY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig PRAIRIE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 16)
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 2), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig GRASSLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 1), new DoubleOrNormalPlacer()).tries(32).build();
    
    public static final RandomPatchFeatureConfig SWITCHGRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SWITCHGRASS.getDefaultState(), 1), new DoubleOrNormalPlacer())
                    .tries(72).build();

    public static final RandomPatchFeatureConfig LUSH_SHRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 2)
                    .addState(EcotonesBlocks.MARIGOLD.getDefaultState(), 2)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 4)
                    .addState(Blocks.GRASS.getDefaultState(), 8), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig DRY_STEPPE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 4)
                    .addState(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 2)
                    .addState(EcotonesBlocks.SANDY_GRASS.getDefaultState(), 4)
                    .addState(Blocks.GRASS.getDefaultState(), 2), new SimpleBlockPlacer()).tries(32).build();
    

    public static final RandomPatchFeatureConfig TAIGA_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 4)
                    .addState(Blocks.FERN.getDefaultState(), 4)
                    .addState(Blocks.LARGE_FERN.getDefaultState(), 1)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 1), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig TALL_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .addState(Blocks.GRASS.getDefaultState(), 6)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 3), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig ONLY_TALL_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 1), new DoubleOrNormalPlacer())
                    .tries(96).build();

    public static final RandomPatchFeatureConfig BLUEBELL_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.BLUEBELL.getDefaultState(), 32)
                    .addState(Blocks.GRASS.getDefaultState(), 4)
                    .addState(Blocks.TALL_GRASS.getDefaultState(), 2), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig DESERT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SANDY_GRASS.getDefaultState(), 1), new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(24).build();

    // flowers
    public static final RandomPatchFeatureConfig TAIGA_FLOWERS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(64).build();

    public static final RandomPatchFeatureConfig WIDE_FERNS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.WIDE_FERN.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(11)
                    .spreadZ(11)
                    .tries(48).build();

    public static final RandomPatchFeatureConfig SMALL_LILAC =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SMALL_LILAC.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(12).build();

    public static final RandomPatchFeatureConfig LAVENDER =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.LAVENDER.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(5)
                    .spreadZ(5)
                    .tries(12).build();

    public static final RandomPatchFeatureConfig CYAN_ROSE =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.CYAN_ROSE.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(32).build();

    // misc
    public static final RandomPatchFeatureConfig SURFACE_ROCKS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.SURFACE_ROCK.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .tries(4).build();

    public static final RandomPatchFeatureConfig CLOVER =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.CLOVER.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(15)
                    .spreadZ(15)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(12).build();

    public static final RandomPatchFeatureConfig MOSS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(EcotonesBlocks.MOSS.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(23)
                    .spreadZ(23)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(16).build();

    public static final RandomPatchFeatureConfig PUMPKIN =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(Blocks.PUMPKIN.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(12)
                    .spreadZ(12)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(64).build();

    public static final RandomPatchFeatureConfig COBWEB =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(Blocks.COBWEB.getDefaultState(), 1),
                    new SimpleBlockPlacer())
                    .spreadX(8)
                    .spreadZ(8)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(12).build();

    public static final RandomPatchFeatureConfig LARGE_CACTUS_PATCH =
            new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.CACTUS.getDefaultState()),
                    new ColumnPlacer(1, 2))
                    .tries(32).cannotProject().build();

    public static final WaterFeatureConfig GRASS_WATER_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState()), Optional.empty(), 6);

    public static final WaterFeatureConfig GRANITE_WATER_POOL_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRANITE.getDefaultState()), Optional.of(new SimpleBlockStateProvider(Blocks.GRANITE.getDefaultState())), 3);

    public static final WaterFeatureConfig GRANITE_WATER_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRANITE.getDefaultState()), Optional.empty(), 12);

    public static final RockSpireFeatureConfig STONE_SPIRE =
            new RockSpireFeatureConfig(new SimpleBlockStateProvider(Blocks.STONE.getDefaultState()));

    public static final RockSpireFeatureConfig GRANITE_SPIRE =
            new RockSpireFeatureConfig(new SimpleBlockStateProvider(Blocks.GRANITE.getDefaultState()));

    // trees
    public static final TreeFeatureConfig SPRUCE_TREE_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
                    new SpruceFoliagePlacer(UniformIntDistribution.of(2, 1), UniformIntDistribution.of(0, 2), UniformIntDistribution.of(1, 1)),
                    new StraightTrunkPlacer(6, 4, 3),
                    new TwoLayersFeatureSize(2, 0, 2))
                    .ignoreVines().build().setTreeDecorators(ImmutableList.of(new PineconeTreeDecorator(6), new LichenTreeDecorator(12), new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 12, 4)));

    public static final TreeFeatureConfig SMALL_PINE_CONFIG = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
            new SmallPineFoliagePlacer(UniformIntDistribution.of(1, 0), UniformIntDistribution.of(0, 1), UniformIntDistribution.of(4, 2)),
            new StraightTrunkPlacer(9, 6, 0),
            new TwoLayersFeatureSize(2, 0, 2))
            .ignoreVines().build()
            .setTreeDecorators(ImmutableList.of(new PineconeTreeDecorator(2), new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 6, 4)));

    public static final TreeFeatureConfig JUNGLE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.JUNGLE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.JUNGLE_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
            new StraightTrunkPlacer(4, 8, 0), new TwoLayersFeatureSize(1, 0, 1))
            .decorators(ImmutableList.of(new CocoaBeansTreeDecorator(0.2F), TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).ignoreVines().build();

    public static final TreeFeatureConfig MEGA_JUNGLE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.JUNGLE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.JUNGLE_LEAVES.getDefaultState()),
            new JungleFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 2),
            new MegaJungleTrunkPlacer(10, 2, 19),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).build();

    public static final TreeFeatureConfig DRY_STEPPE_TREE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
            new PlusLeavesFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0)),
            new LargeOakTrunkPlacer(8, 6, 0),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
            .ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build();
}
