package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
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
import supercoder79.ecotones.world.features.config.RockFeatureConfig;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class WhiteMesaBiome extends EcotonesBiome {
    public static WhiteMesaBiome INSTANCE;
    public static WhiteMesaBiome PLATEAU;
    public static WhiteMesaBiome HILLY;
    public static WhiteMesaBiome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "white_mesa"), new WhiteMesaBiome(0.4F, 0.05F, 2, 0.96));
        PLATEAU = Registry.register(Registry.BIOME, new Identifier("ecotones", "white_mesa_plateau"), new WhiteMesaBiome(1.6F, 0.1F, 5, 1.2));
        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "white_mesa_hilly"), new WhiteMesaBiome(0.6F, 0.125F, 4, 0.87));
        MOUNTAINOUS = Registry.register(Registry.BIOME, new Identifier("ecotones", "white_mesa_mountainous"), new WhiteMesaBiome(0.8F, 0.325F, 5, 0.82));
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 1);
        BiomeRegistries.registerBiomeVariants(INSTANCE, INSTANCE, PLATEAU);
        Climate.WARM_VERY_DRY.add(INSTANCE, 0.1);
    }

    public WhiteMesaBiome(float depth, float scale, double hilliness, double volatility) {
        super(new Settings()
                        .configureSurfaceBuilder(EcotonesSurfaces.WHITE_MESA, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Precipitation.RAIN)
                        .category(Category.PLAINS)
                        .depth(depth)
                        .scale(scale)
                        .temperature(1.2F)
                        .downfall(0.315F)
                        .effects(new BiomeEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .build()).parent(null)
                        .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                hilliness,
                volatility);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addPlainsTallGrass(this);
        DefaultBiomeFeatures.method_28440(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        DefaultBiomeFeatures.addSavannaGrass(this);
        DefaultBiomeFeatures.addSavannaTallGrass(this);

        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/savanna/town_centers"), 4)));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2.1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 7, 9))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_ACACIA.configure(TreeType.MEDIUM_RARE_ACACIA)
                        .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.MEDIUM_RARE_ACACIA.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(7, 0.25f, 3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.TREE.configure(FeatureConfigHolder.DEAD_LARGE_OAK)
                        .createDecoratedFeature(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.45))));

        BiomeUtil.addDefaultFeatures(this);
        BiomeUtil.addDefaultSpawns(this);
    }
}
