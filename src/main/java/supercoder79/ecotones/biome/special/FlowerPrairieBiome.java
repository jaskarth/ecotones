package supercoder79.ecotones.biome.special;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
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
import supercoder79.ecotones.biome.BiomeUtil;
import supercoder79.ecotones.biome.EcotonesBiome;
import supercoder79.ecotones.decorator.EcotonesDecorators;
import supercoder79.ecotones.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.FeatureConfigHolder;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;

public class FlowerPrairieBiome extends EcotonesBiome {
    public static FlowerPrairieBiome INSTANCE;
    public static FlowerPrairieBiome HILLY;
    public static FlowerPrairieBiome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "flower_prairie"), new FlowerPrairieBiome(0.5F, 0.025F));
        BiomeRegistries.registerSpecialBiome(INSTANCE, id -> Registry.BIOME.get(id).getName().asString().equals("biome.ecotones.prairie"));

        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "flower_prairie_hilly"), new FlowerPrairieBiome(1.25F, 0.225F));
        BiomeRegistries.registerSpecialBiome(HILLY, id -> Registry.BIOME.get(id).getName().asString().equals("biome.ecotones.prairie_hilly"));

        MOUNTAINOUS = Registry.register(Registry.BIOME, new Identifier("ecotones", "flower_prairie_mountainous"), new FlowerPrairieBiome(2F, 0.625F));
        BiomeRegistries.registerSpecialBiome(MOUNTAINOUS, id -> Registry.BIOME.get(id).getName().asString().equals("biome.ecotones.prairie_mountainous"));

        BiomeRegistries.registerSmallSpecialBiome(INSTANCE, 10);
        BiomeRegistries.registerSmallSpecialBiome(HILLY, 10);
        BiomeRegistries.registerSmallSpecialBiome(MOUNTAINOUS, 10);
    }

    public FlowerPrairieBiome(float depth, float scale) {
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
                1,
                0.9);
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
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.PRAIRIE_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 20, 30))));
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))));
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2))));

        DefaultBiomeFeatures.addForestFlowers(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_RANDOM_SELECTOR.configure(
                        new RandomRandomFeatureConfig(
                                ImmutableList.of(
                                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.LILAC_CONFIG),
                                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.ROSE_BUSH_CONFIG),
                                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.PEONY_CONFIG),
                                        Feature.FLOWER.configure(DefaultBiomeFeatures.LILY_OF_THE_VALLEY_CONFIG)),
                                2))
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(10))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.FLOWER.configure(DefaultBiomeFeatures.FOREST_FLOWER_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(100))));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.HORSE, 5, 2, 6));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.DONKEY, 1, 1, 3));
        this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1));

        BiomeUtil.addDefaultFeatures(this);
    }

    @Override
    public int getGrassColorAt(double x, double z) {
        return 0xabcf59;
    }

    @Override
    public int getFoliageColor() {
        return 0xabcf59;
    }
}
