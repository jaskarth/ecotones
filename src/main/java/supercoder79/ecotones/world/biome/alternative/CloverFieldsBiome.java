package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
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

public class CloverFieldsBiome extends EcotonesBiome {
    public static CloverFieldsBiome INSTANCE;
    public static CloverFieldsBiome THICKET;
    public static CloverFieldsBiome FLATS;
    public static CloverFieldsBiome HILLS;
    public static CloverFieldsBiome SHRUB;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "clover_fields"), new CloverFieldsBiome(0.5F, 0.05F, 0, 1, 2.4, 0.95));
        THICKET = Registry.register(Registry.BIOME, new Identifier("ecotones", "clover_thicket"), new CloverFieldsBiome(0.5F, 0.025F, 2, 2, 2.8, 0.9));
        FLATS = Registry.register(Registry.BIOME, new Identifier("ecotones", "clover_flats"), new CloverFieldsBiome(0.5F, 0.01F, 0, 2, 1.2, 1.2));
        HILLS = Registry.register(Registry.BIOME, new Identifier("ecotones", "clover_hills"), new CloverFieldsBiome(1.2F, 0.625F, 0, 1, 4.8, 0.75));
        SHRUB = Registry.register(Registry.BIOME, new Identifier("ecotones", "clover_shrubs"), new CloverFieldsBiome(0.5F, 0.025F, 0, 4, 2.8, 0.9));

        BiomeRegistries.registerAllSpecial(id ->
                BiomeUtil.contains(id, "lichen_woodland") || BiomeUtil.contains(id, "spruce_forest") || BiomeUtil.contains(id, "prairie")
                , INSTANCE, THICKET, FLATS, HILLS, SHRUB);

        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 45);
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 2);
        BiomeRegistries.registerBiomeVariants(INSTANCE, THICKET, FLATS, HILLS, SHRUB);
        Climate.WARM_MODERATE.add(INSTANCE, 0.1);
        Climate.WARM_MILD.add(INSTANCE, 0.1);
        Climate.WARM_HUMID.add(INSTANCE, 0.1);
    }

    public CloverFieldsBiome(float depth, float scale, int treeSpawnAmt, int shrubSpawnAmt, double hilliness, double volatility) {
        super((new Biome.Settings())
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Biome.Precipitation.RAIN)
                        .category(Biome.Category.PLAINS)
                        .depth(depth)
                        .scale(scale)
                        .temperature(1F)
                        .downfall(0.4F)
                        .effects(new BiomeEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .build()).parent(null)
                        .noises(ImmutableList.of(new Biome.MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                hilliness,
                volatility);

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.VILLAGE.configure(new StructurePoolFeatureConfig("village/taiga/town_centers", 4)));
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
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 6, 14))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(14))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(shrubSpawnAmt))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(treeSpawnAmt, 0.25f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(treeSpawnAmt, 0.25f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.SWEET_BERRY_BUSH_CONFIG)
                .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(2))));

        BiomeUtil.addDefaultSpawns(this);
        BiomeUtil.addDefaultFeatures(this);
    }

    @Override
    public int getGrassColorAt(double x, double y) {
        return 0x308046;
    }

    @Override
    public int getFoliageColor() {
        return 0x308046;
    }
}
