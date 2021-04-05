package supercoder79.ecotones.world.biome.special;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.biome.technical.TropicalBeachBiome;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class ThePitsBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome EDGE;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "the_pits"), new ThePitsBiome(-1.5f, false).build());
        EDGE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "the_pits_edge"), new ThePitsBiome(0.375f, true).build());

        BiomeRegistries.registerAllSpecial(id -> !BiomeUtil.isOcean(id), INSTANCE, EDGE);

        BiomeRegistries.registerSmallSpecialBiome(INSTANCE, 200);
    }

    protected ThePitsBiome(float height, boolean isEdge) {
        this.category(Biome.Category.EXTREME_HILLS);
        this.surfaceBuilder(EcotonesSurfaces.DELETE_WATER_BUILDER, SurfaceBuilder.GRASS_CONFIG);

        this.depth(height);
        this.scale(0.0125F);
        this.temperature(1.6F);
        this.downfall(0.4F);

        this.precipitation(Biome.Precipitation.RAIN);
        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        if (!isEdge) {
            this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                    Feature.FOREST_ROCK.configure(new SingleStateFeatureConfig(Blocks.STONE.getDefaultState()))
                            .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                            .spreadHorizontally()
                            .applyChance(2));

//            this.addFeature(GenerationStep.Feature.RAW_GENERATION,
//                    Feature.FOREST_ROCK.configure(new ForestRockFeatureConfig(Blocks.STONE.getDefaultState(), 2))
//                            .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(5))));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(12));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.getDefaultState(), 9))
                        .rangeOf(64)
                        .spreadHorizontally()
                        .repeat(30));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.GOLD_ORE.getDefaultState(), 9))
                        .rangeOf(32)
                        .spreadHorizontally()
                        .repeat(6));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.LAVA.getDefaultState()))
                        .decorate(Decorator.LAVA_LAKE.configure(new ChanceDecoratorConfig(40))));

        this.addSpawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
