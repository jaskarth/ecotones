package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class LushDesertBiome extends EcotonesBiome {
    public static LushDesertBiome INSTANCE;
    public static LushDesertBiome HILLY;
    public static LushDesertBiome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "lush_desert"), new LushDesertBiome(0.5f, 0.3f, 2.4, 0.94));
        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "lush_desert_hilly"), new LushDesertBiome(1f, 0.5f, 4.2, 0.87));
        MOUNTAINOUS = Registry.register(Registry.BIOME, new Identifier("ecotones", "lush_desert_mountainous"), new LushDesertBiome(1.75f, 0.8f, 8, 0.81));
        BiomeRegistries.registerNoBeachBiome(INSTANCE);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.HOT_DESERT.add(INSTANCE, 0.3);
        Climate.WARM_DESERT.add(INSTANCE, 0.12);
    }


    protected LushDesertBiome(float depth, float scale, double hilliness, double volatility) {
        super((new Settings())
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_CONFIG)
                        .precipitation(Precipitation.RAIN)
                        .category(Category.PLAINS)
                        .depth(depth)
                        .scale(scale)
                        .temperature(2F)
                        .downfall(0.3F)
                        .effects((new BiomeEffects.Builder())
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .particleConfig(new BiomeParticleConfig(EcotonesParticles.SAND, 0.00325F))
                                .build()).parent(null)
                        .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                hilliness,
                volatility);

        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/desert/town_centers"), 8)));
        this.addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST);
        this.addStructureFeature(DefaultBiomeFeatures.DESERT_PYRAMID);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.CACTUS_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(80))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.DEAD_BUSH_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(16))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.DESERT_GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_32
                                .configure(new NoiseHeightmapDecoratorConfig(-0.8D, 9, 12))));

        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.method_28440(this);
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
