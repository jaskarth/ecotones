package supercoder79.ecotones.biome.special;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.biome.BiomeUtil;
import supercoder79.ecotones.biome.EcotonesBiome;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.FeatureConfigHolder;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;

public class WoodlandThicketBiome extends EcotonesBiome {
    public static WoodlandThicketBiome INSTANCE;
    public static WoodlandThicketBiome CLEARING;
    public static WoodlandThicketBiome HILLY;
    public static WoodlandThicketBiome HILLY_CLEARING;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "woodland_thicket"), new WoodlandThicketBiome(0.5F, 0.125F, 2.1, 1, 5));
        CLEARING = Registry.register(Registry.BIOME, new Identifier("ecotones", "woodland_clearing"), new WoodlandThicketBiome(0.5F, 0.0F, 1.2, 0.88, 2));
        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "woodland_hilly"), new WoodlandThicketBiome(1.2F, 0.8F, 3.2, 0.6, 5));
        HILLY_CLEARING = Registry.register(Registry.BIOME, new Identifier("ecotones", "woodland_clearing_hilly"), new WoodlandThicketBiome(1.2F, 0.2F, 1.6, 0.8, 2));

        BiomeRegistries.registerAllSpecial(id -> {
            String name = Registry.BIOME.get(id).getName().asString();

            //don't replace rainforests
            if (name.contains("rainforest")) return false;

            return name.contains("prairie") || name.contains("forest") || name.contains("woodland");
        }, Registry.BIOME.getRawId(INSTANCE), Registry.BIOME.getRawId(CLEARING), Registry.BIOME.getRawId(HILLY), Registry.BIOME.getRawId(HILLY_CLEARING));

        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 30);

        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 4);
        BiomeRegistries.registerBiomeVariants(INSTANCE, CLEARING, HILLY, HILLY_CLEARING);
    }

    public WoodlandThicketBiome(float depth, float scale, double hilliness, double volatility, int treeAmt) {
        super((new Settings())
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Precipitation.RAIN)
                .category(Category.PLAINS)
                .depth(depth)
                .scale(scale)
                .temperature(1F)
                .downfall(0.4F)
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
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 10, 20))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(8, 0.5f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.FANCY_TREE.configure(DefaultBiomeFeatures.FANCY_TREE_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(treeAmt, 0.65F, 2))));

        BiomeUtil.addDefaultSpawns(this);
    }
}
