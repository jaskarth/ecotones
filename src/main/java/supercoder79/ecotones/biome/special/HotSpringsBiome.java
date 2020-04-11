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
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.FeatureConfigHolder;
import supercoder79.ecotones.features.config.ShrubConfig;
import supercoder79.ecotones.surface.EcotonesSurfaces;

public class HotSpringsBiome extends Biome {
    public static HotSpringsBiome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "hot_springs"), new HotSpringsBiome());
        BiomeRegistries.registerSpecialBiome(Registry.BIOME.getRawId(INSTANCE), id -> true);
    }

    protected HotSpringsBiome() {
        super((new Biome.Settings())
                .configureSurfaceBuilder(EcotonesSurfaces.HOT_SPRINGS_BUILDER, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.PLAINS)
                .depth(0.25F)
                .scale(0.0125F)
                .temperature(1.6F)
                .downfall(0.4F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(4445678)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .build()).parent(null)
                .noises(ImmutableList.of(new Biome.MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))));
        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 6, 8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.PINE_TREE_CONFIG).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.66f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.SPRUCE_TREE_CONFIG).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.33f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState())).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.166f, 1))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.LAVA.getDefaultState())).createDecoratedFeature(Decorator.LAVA_LAKE.configure(new ChanceDecoratorConfig(30))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).createDecoratedFeature(Decorator.WATER_LAKE.configure(new ChanceDecoratorConfig(1))));
        this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
