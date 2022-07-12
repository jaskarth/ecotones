package supercoder79.ecotones.world.biome.unused;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import supercoder79.ecotones.world.decorator.CountExtraDecoratorConfig;
import supercoder79.ecotones.world.decorator.Spread32Decorator;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;
import supercoder79.ecotones.world.features.mc.RandomPatchFeatureConfig;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

// TODO: rewrite
public class SwampBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "swamp"), new SwampBiome(0.5f, 0.075f).build());
//        TerraformSlimeSpawnBiomes.addSlimeSpawnBiomes(BuiltinRegistries.BIOME.getKey(INSTANCE).get());
        Climate.WARM_HUMID.add(INSTANCE, 0.1);
        BiomeRegistries.registerNoBeachBiome(INSTANCE);
        BiomeRegistries.registerNoRiverBiome(INSTANCE);
    }

    protected SwampBiome(float depth, float scale) {
        this.surfaceBuilder(EcotonesSurfaces.PEAT_SWAMP_BUILDER, SurfaceBuilder.SAND_CONFIG);
        this.precipitation(Biome.Precipitation.RAIN);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.5F);
        this.downfall(1.4F);

        this.waterColor(0x2e8a07);
        this.waterFogColor(0x2e8a07);

        this.grassColor(0x286620);
        this.foliageColor(0x286620);

        this.hilliness(1.2);
        this.volatility(0.96);

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());
//        this.addStructureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_SWAMP);
//        this.addStructureFeature(ConfiguredStructureFeatures.MINESHAFT);

        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        //DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.GRASS_WATER_PATCH)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.5f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.REEDS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 4, 7)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.5f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_LARGE_CLUSTERED_OAK)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(BlockStateProvider.of(Blocks.LILY_PAD.getDefaultState())).tries(10).build())
                        .repeat(12)
                        .spreadHorizontally()
                        .decorate(new Spread32Decorator()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesConfiguredFeature.wrap(Feature.SEAGRASS, (new ProbabilityConfig(0.3f)))
                .repeat(20)
                .spreadHorizontally()
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .repeat(4)
                        .spreadHorizontally()
                        .decorate(new Spread32Decorator()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .repeat(4)
                        .spreadHorizontally()
                        .decorate(new Spread32Decorator()));

//        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
//                        .applyChance(15)
//                        .spreadHorizontally()
//                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));
//
//        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 0))
//                        .applyChance(9)
//                        .spreadHorizontally()
//                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));
    }
}
