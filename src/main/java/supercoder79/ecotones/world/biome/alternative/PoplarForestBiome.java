package supercoder79.ecotones.world.biome.alternative;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountNoiseDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class PoplarForestBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome CLEARING;
    public static Biome THICKET;
    public static Biome FLATS;
    public static Biome HILLS;
    public static Biome SHRUB;

    public static Biome BIRCH;
    public static Biome OAK;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest"), new PoplarForestBiome(0.5F, 0.05F, 3, 2, 1, 2.4, 0.95).build());
        CLEARING = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_clearing"), new PoplarForestBiome(0.5F, 0.05F, 0, 0, 1, 2.4, 0.95).build());
        THICKET = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_thicket"), new PoplarForestBiome(0.5F, 0.025F, 6, 6, 2, 2.8, 0.9).build());
        FLATS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_flats"), new PoplarForestBiome(0.5F, 0.01F, 2, 2, 2, 1.2, 1.2).build());
        HILLS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_hills"), new PoplarForestBiome(1.2F, 0.625F, 3, 2, 1, 4.8, 0.75).build());
        SHRUB = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_shrubs"), new PoplarForestBiome(0.5F, 0.025F, 2, 2, 4, 2.8, 0.9).build());

        OAK = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_oak"), new PoplarForestBiome(0.5F, 0.05F, 5, 1, 1, 2.4, 0.95).build());
        BIRCH = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "poplar_forest_birch"), new PoplarForestBiome(0.5F, 0.05F, 1, 5, 1, 2.4, 0.95).build());

        BiomeRegistries.registerAllSpecial(id ->
                BiomeUtil.contains(id, "lichen_woodland") || BiomeUtil.contains(id, "spruce_forest") || BiomeUtil.contains(id, "prairie"),
                INSTANCE, THICKET, FLATS, HILLS, SHRUB);

        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 40);

        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 2);
        BiomeRegistries.registerBiomeVariants(INSTANCE, CLEARING, THICKET, FLATS, HILLS, SHRUB, OAK, BIRCH);
        Climate.WARM_VERY_HUMID.add(INSTANCE, 0.3);
        Climate.HOT_VERY_HUMID.add(INSTANCE, 0.2);
        Climate.HOT_HUMID.add(INSTANCE, 0.15);
    }

    public PoplarForestBiome(float depth, float scale, int oakTreeSpawnAmt, int birchTreeSpawnAmt, int shrubSpawnAmt, double hilliness, double volatility) {
        this.category(Biome.Category.FOREST);
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.7F);
        this.downfall(0.6F);

        this.precipitation(Biome.Precipitation.RAIN);

        this.hilliness(hilliness);
        this.volatility(volatility);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsTallGrass(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsFeatures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure(DecoratorConfig.DEFAULT)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 6, 14))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(shrubSpawnAmt))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(oakTreeSpawnAmt + 0.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.03))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(birchTreeSpawnAmt + 0.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.45))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_LILAC)
                        .repeat(1)
                        .spreadHorizontally()
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE)));

        BiomeUtil.addDefaultSpawns(this.getSpawnSettings());
        BiomeUtil.addDefaultFeatures(this);
    }
}
