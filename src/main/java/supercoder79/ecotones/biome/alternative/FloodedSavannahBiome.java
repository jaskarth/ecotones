package supercoder79.ecotones.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.biome.BiomeUtil;
import supercoder79.ecotones.biome.EcotonesBiome;
import supercoder79.ecotones.decorator.EcotonesDecorators;
import supercoder79.ecotones.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;

public class FloodedSavannahBiome extends EcotonesBiome {
    public static FloodedSavannahBiome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "flooded_savannah"), new FloodedSavannahBiome());
        Climate.HOT_MILD.add(INSTANCE, 0.1);
        Climate.HOT_MODERATE.add(INSTANCE, 0.2);
        Climate.HOT_DRY.add(INSTANCE, 0.1);
        Climate.HOT_VERY_DRY.add(INSTANCE, 0.05);
    }


    protected FloodedSavannahBiome() {
        super((new Biome.Settings())
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Biome.Precipitation.RAIN)
                        .category(Biome.Category.PLAINS)
                        .depth(-0.09F)
                        .scale(0.0F)
                        .temperature(1.6F)
                        .downfall(0.65F)
                        .effects((new BiomeEffects.Builder())
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .build()).parent(null)
                        .noises(ImmutableList.of(new Biome.MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                2,
                1.2);

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(4))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Feature.FOREST_ROCK.configure(new BoulderFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 0))
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(12))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_ACACIA.configure(TreeType.ACACIA.config)
                        .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.ACACIA.config.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_LARGE_OAK.config)
                        .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_OAK.config.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE
                                .configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))));

        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        BiomeUtil.addDefaultSpawns(this);
        BiomeUtil.addDefaultFeatures(this);
    }
}
