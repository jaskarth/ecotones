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
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class WhiteMesaBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome PLATEAU;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "white_mesa"), new WhiteMesaBiome(0.4F, 0.05F, 3, 0.96).build());
        PLATEAU = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "white_mesa_plateau"), new WhiteMesaBiome(1.6F, 0.1F, 5, 1.2).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "white_mesa_hilly"), new WhiteMesaBiome(0.6F, 0.125F, 4, 0.87).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "white_mesa_mountainous"), new WhiteMesaBiome(0.8F, 0.325F, 5, 0.82).build());
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 1);
        BiomeRegistries.registerBiomeVariants(INSTANCE, INSTANCE, PLATEAU);
        Climate.WARM_VERY_DRY.add(INSTANCE, 0.1);
        Climate.HOT_VERY_DRY.add(INSTANCE, 0.1);
    }

    public WhiteMesaBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.WHITE_MESA, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1.2F);
        this.downfall(0.315F);

        this.precipitation(Biome.Precipitation.RAIN);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.category(Biome.Category.MESA);

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());

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

//        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> SavannaVillageData.STRUCTURE_POOLS, 4)));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2.1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 7, 9)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_ACACIA.configure(TreeType.MEDIUM_RARE_ACACIA)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.MEDIUM_RARE_ACACIA.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(7, 0.25f, 3)))
                        .spreadHorizontally()
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.45))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        BiomeHelper.addDefaultFeatures(this);
        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
