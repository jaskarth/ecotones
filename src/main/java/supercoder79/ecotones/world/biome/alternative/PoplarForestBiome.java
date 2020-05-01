package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class PoplarForestBiome extends EcotonesBiome {
    public static PoplarForestBiome INSTANCE;
    public static PoplarForestBiome CLEARING;
    public static PoplarForestBiome THICKET;
    public static PoplarForestBiome FLATS;
    public static PoplarForestBiome HILLS;
    public static PoplarForestBiome SHRUB;

    public static PoplarForestBiome BIRCH;
    public static PoplarForestBiome OAK;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest"), new PoplarForestBiome(0.5F, 0.05F, 3, 2, 1, 2.4, 0.95));
        CLEARING = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_clearing"), new PoplarForestBiome(0.5F, 0.05F, 0, 0, 1, 2.4, 0.95));
        THICKET = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_thicket"), new PoplarForestBiome(0.5F, 0.025F, 6, 6, 2, 2.8, 0.9));
        FLATS = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_flats"), new PoplarForestBiome(0.5F, 0.01F, 2, 2, 2, 1.2, 1.2));
        HILLS = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_hills"), new PoplarForestBiome(1.2F, 0.625F, 3, 2, 1, 4.8, 0.75));
        SHRUB = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_shrubs"), new PoplarForestBiome(0.5F, 0.025F, 2, 2, 4, 2.8, 0.9));

        OAK = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_oak"), new PoplarForestBiome(0.5F, 0.05F, 5, 1, 1, 2.4, 0.95));
        BIRCH = Registry.register(Registry.BIOME, new Identifier("ecotones", "poplar_forest_birch"), new PoplarForestBiome(0.5F, 0.05F, 1, 5, 1, 2.4, 0.95));

        BiomeRegistries.registerAllSpecial(id ->
                BiomeUtil.contains(id, "lichen_woodland") || BiomeUtil.contains(id, "spruce_forest") || BiomeUtil.contains(id, "prairie")
                , INSTANCE, THICKET, FLATS, HILLS, SHRUB);

        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 40);

        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 2);
        BiomeRegistries.registerBiomeVariants(INSTANCE, CLEARING, THICKET, FLATS, HILLS, SHRUB, OAK, BIRCH);
        Climate.WARM_VERY_HUMID.add(INSTANCE, 0.3);
        Climate.HOT_VERY_HUMID.add(INSTANCE, 0.2);
        Climate.HOT_HUMID.add(INSTANCE, 0.15);
    }

    public PoplarForestBiome(float depth, float scale, int oakTreeSpawnAmt, int birchTreeSpawnAmt, int shrubSpawnAmt, double hilliness, double volatility) {
        super((new Settings())
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Precipitation.RAIN)
                        .category(Category.PLAINS)
                        .depth(depth)
                        .scale(scale)
                        .temperature(0.7F)
                        .downfall(0.6F)
                        .effects(new BiomeEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .build()).parent(null)
                        .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                hilliness,
                volatility);

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addPlainsTallGrass(this);
        DefaultBiomeFeatures.addPlainsFeatures(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .createDecoratedFeature(EcotonesDecorators.ROCKINESS.configure(DecoratorConfig.DEFAULT)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 6, 14))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(shrubSpawnAmt))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(oakTreeSpawnAmt, 0.25f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(birchTreeSpawnAmt, 0.25f, 1))));

        BiomeUtil.addDefaultSpawns(this);
        BiomeUtil.addDefaultFeatures(this);
    }
}
