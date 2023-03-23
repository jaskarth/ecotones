package supercoder79.ecotones.world.biome.base.hot;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.structure.DesertVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.util.compat.LambdaFoxesCompat;
import supercoder79.ecotones.world.biome.BiomeAssociations;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.*;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.features.mc.RandomPatchFeatureConfig;
import supercoder79.ecotones.world.river.deco.CommonRiverDecorations;
import supercoder79.ecotones.world.surface.DesertScrubSurfaceBuilder;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class ScrublandBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "scrubland"), new ScrublandBiome(0.5f, 0.075f, 1.6, 1).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "scrubland_hilly"), new ScrublandBiome(1f, 0.5f, 4.2, 0.87).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "scrubland_mountainous"), new ScrublandBiome(1.75f, 0.8f, 8, 0.81).build());
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);

        Climate.HOT_VERY_DRY.add(INSTANCE, 1);

        BiomeRegistries.registerRiverDecorator(INSTANCE, CommonRiverDecorations::buildDesertLushness);
    }

    protected ScrublandBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.DESERT_SCRUB_BUILDER, DesertScrubSurfaceBuilder.SCRUB_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.9F);
        this.downfall(0.2F);
        this.particleConfig(new BiomeParticleConfig(EcotonesParticles.SAND, 0.00125F));

        this.hilliness(hilliness);
        this.volatility(volatility);
        associate(BiomeAssociations.SAVANNA_LIKE);
//        this.category(Biome.Category.SAVANNA);

//        this.addStructureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_DESERT);
//        this.addStructureFeature(ConfiguredStructureFeatures.MINESHAFT);
//         this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());
//        this.addStructureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
//        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> DesertVillageData.STRUCTURE_POOLS, 5)));

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
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.DEAD_BUSH.getDefaultState())).tries(12).build())
                        .decorate(new Spread32Decorator())
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.CACTUS.getDefaultState())).tries(10).cannotProject().build())
                        .decorate(new SpreadDoubleDecorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(40));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.15))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CACTI.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(4.5))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_CACTUS)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 5, 10)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.DESERT_GRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.ABOVE_QUALITY.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.5f, 2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.FLAME_LILY)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(6));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);

        if (LambdaFoxesCompat.isEnabled()) {
            this.addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        }
    }
}
