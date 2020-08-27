package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

// Dedicated to Amelia Rose.
// 
// Thank you for being there when I needed it. Ecotones would have never been the same without it.
// My life probably wouldn't have been the same without it. It fills me with so much grief that it had to end this way.
//
// So.
// This biome is for you. I know it doesn't mean all that much, but.... thank you.
//
// For Amelia Rose, with love. - SuperCoder79
public class RoseFieldBiome extends EcotonesBiome {
    public static RoseFieldBiome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "rose_field"), new RoseFieldBiome());
        Climate.WARM_MILD.add(INSTANCE, 0.1);
        Climate.WARM_HUMID.add(INSTANCE, 0.1);
    }

    public RoseFieldBiome() {
        super(new Settings()
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Precipitation.RAIN)
                .category(Category.PLAINS)
                .depth(0.2F)
                .scale(0.05F)
                .temperature(0.85f)
                .downfall(0.725f)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .build()).parent(null)
                .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                2.0, 0.95);

        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addPlainsTallGrass(this);
        DefaultBiomeFeatures.method_28440(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        BiomeUtil.addDefaultFeatures(this);
        BiomeUtil.addDefaultSpawns(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .createDecoratedFeature(EcotonesDecorators.ROCKINESS.configure(DecoratorConfig.DEFAULT)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8, 12, 16))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.45))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BEEHIVES.configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(100))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.FLOWER.configure(FeatureConfigHolder.CYAN_ROSE)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP.configure(new CountDecoratorConfig(8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_LARGE_CLUSTERED_OAK)
                        .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.decorationData)));
    }
}
