package supercoder79.ecotones.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
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
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.biome.BiomeUtil;
import supercoder79.ecotones.biome.EcotonesBiome;
import supercoder79.ecotones.decorator.EcotonesDecorators;
import supercoder79.ecotones.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.FeatureConfigHolder;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;

public class BirchForestBiome extends EcotonesBiome {
    public static BirchForestBiome INSTANCE;
    public static BirchForestBiome HILLY;
    public static BirchForestBiome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "birch_forest"), new BirchForestBiome(0.5F, 0.025F));
        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "birch_forest_hilly"), new BirchForestBiome(1.25F, 0.225F));
        MOUNTAINOUS = Registry.register(Registry.BIOME, new Identifier("ecotones", "birch_forest_mountainous"), new BirchForestBiome(2F, 0.625F));
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.WARM_VERY_HUMID.add(INSTANCE, 0.2);
        Climate.WARM_HUMID.add(INSTANCE, 0.1);
    }

    public BirchForestBiome(float depth, float scale) {
        super((new Settings())
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Precipitation.RAIN)
                .category(Category.PLAINS)
                .depth(depth)
                .scale(scale)
                .temperature(0.7F)
                .downfall(0.7F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .build()).parent(null)
                .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                3,
                0.9);
        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addPlainsTallGrass(this);
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
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SHORT_GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 16, 20))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.FOREST_BIRCH.config)
                        .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.FOREST_BIRCH.config)));

        DefaultBiomeFeatures.addForestFlowers(this);

        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.HORSE, 5, 2, 6));
        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.DONKEY, 1, 1, 3));
        this.addSpawn(EntityCategory.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));

        BiomeUtil.addDefaultFeatures(this);
    }
}
