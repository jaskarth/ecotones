package supercoder79.ecotones.world.biome.climatic;

import net.minecraft.block.Blocks;
import net.minecraft.structure.SavannaVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import supercoder79.ecotones.world.decorator.*;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class DrySavannaBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome THICKET;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dry_savanna"), new DrySavannaBiome(0.5F, 0.025F, 3, 0.96, false).build());
        THICKET = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dry_savanna_thicket"), new DrySavannaBiome(0.5F, 0.025F, 3, 0.96, true).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dry_savanna_hilly"), new DrySavannaBiome(1.25F, 0.125F, 6, 0.87, false).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dry_savanna_mountainous"), new DrySavannaBiome(2F, 0.325F, 8, 0.82, false).build());
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 3);
        BiomeRegistries.registerBiomeVariants(INSTANCE, INSTANCE, THICKET);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.HOT_VERY_DRY.add(INSTANCE, 0.5);
        Climate.WARM_VERY_DRY.add(INSTANCE, 0.3);
    }

    public DrySavannaBiome(float depth, float scale, double hilliness, double volatility, boolean thicket) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1.2f);
        this.downfall(0.025f);
        this.precipitation(Biome.Precipitation.NONE);
        this.category(Biome.Category.SAVANNA);

        this.hilliness(hilliness);
        this.volatility(volatility);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsTallGrass(this.getGenerationSettings());
        //DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());
        DefaultBiomeFeatures.addSavannaGrass(this.getGenerationSettings());
        DefaultBiomeFeatures.addSavannaTallGrass(this.getGenerationSettings());

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());
//        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> SavannaVillageData.STRUCTURE_POOLS, 7)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure()));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(thicket ? 3.2 : 2.1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 7, 9)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SMALL_ACACIA.configure(TreeType.SMALL_ACACIA)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.SMALL_ACACIA.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ROSEMARY.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.ROSEMARY.configure(new ShrubDecoratorConfig(0.15))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.15))));

        if (thicket) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.BRANCHING_ACACIA.configure(TreeType.ACACIA)
                            .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.ACACIA.decorationData)));
        } else {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.BRANCHING_ACACIA.configure(TreeType.RARE_ACACIA)
                            .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_ACACIA.decorationData)));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(12, 0.25f, 4)))
                        .spreadHorizontally()
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(thicket ? 0.35 : 0.15))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.STONE_SPIRE)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(12));

        BiomeHelper.addDefaultFeatures(this);
        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
