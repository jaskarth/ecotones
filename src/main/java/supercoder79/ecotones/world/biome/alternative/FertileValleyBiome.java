package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class FertileValleyBiome extends EcotonesBiome {
    public static FertileValleyBiome INSTANCE;
    public static FertileValleyBiome CLEARING;
    public static FertileValleyBiome THICKET;
    public static FertileValleyBiome HILLY;
    public static FertileValleyBiome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "fertile_valley"), new FertileValleyBiome(0.2F, 0.025F, 0.6, 0.15));
        CLEARING = Registry.register(Registry.BIOME, new Identifier("ecotones", "fertile_valley_clearing"), new FertileValleyBiome(0.2F, 0.025F, 0.1, 0.35));
        THICKET = Registry.register(Registry.BIOME, new Identifier("ecotones", "fertile_valley_thicket"), new FertileValleyBiome(0.2F, 0.025F, 1.6, 0.4));
        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "fertile_valley_hilly"), new FertileValleyBiome(0.3F, 0.225F, 0.6, 0.15));
        MOUNTAINOUS = Registry.register(Registry.BIOME, new Identifier("ecotones", "fertile_valley_mountainous"), new FertileValleyBiome(0.5F, 0.625F, 0.6, 0.15));
        BiomeRegistries.registerNoBeachBiomes(INSTANCE, THICKET, CLEARING, HILLY, MOUNTAINOUS);
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 3);
        BiomeRegistries.registerBiomeVariants(INSTANCE, THICKET, CLEARING);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.WARM_MODERATE.add(INSTANCE, 0.2);
        Climate.WARM_MILD.add(INSTANCE, 0.3);
    }

    public FertileValleyBiome(float depth, float scale, double treeAmt, double wideShrubAmt) {
        super(new Settings()
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Precipitation.RAIN)
                        .category(Category.PLAINS)
                        .depth(depth)
                        .scale(scale)
                        .temperature(0.9F)
                        .downfall(0.5F)
                        .effects(new BiomeEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .build()).parent(null)
                        .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                4,
                1.1);
        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/plains/town_centers"), 8)));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addSavannaGrass(this);
        DefaultBiomeFeatures.addPlainsTallGrass(this);
        DefaultBiomeFeatures.addPlainsFeatures(this);
        DefaultBiomeFeatures.method_28440(this);
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
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.TALL_GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 6, 12))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.4 + (wideShrubAmt / 4)))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.config)
                        .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.config.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(wideShrubAmt))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.FARMLAND.configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP.configure(new CountDecoratorConfig(2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeAmt))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_LILAC)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.BEEHIVES.configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(16))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.33f, 1))));

        DefaultBiomeFeatures.addForestFlowers(this);

        BiomeUtil.addDefaultFeatures(this);
        BiomeUtil.addDefaultSpawns(this);
    }

    @Override
    public int getGrassColorAt(double x, double y) {
        return 0x5db85a;
    }

    @Override
    public int getFoliageColor() {
        return 0x44a341;
    }
}
