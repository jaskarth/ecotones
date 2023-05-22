package supercoder79.ecotones.world.biome.unused;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import supercoder79.ecotones.world.biome.EarlyBiomeRegistry;
import supercoder79.ecotones.world.decorator.Spread32Decorator;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;
import net.minecraft.world.gen.YOffset;

public class VolcanicBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;

    public static void init() {
        INSTANCE = EarlyBiomeRegistry.register(new Identifier("ecotones", "volcanic_biome"), new VolcanicBiome().build());
        BiomeRegistries.registerSpecialBiome(INSTANCE, id -> true);
    }

    protected VolcanicBiome() {
        this.surfaceBuilder(EcotonesSurfaces.VOLCANIC_BUILDER, SurfaceBuilder.GRASS_CONFIG);

        this.depth(0.25F);
        this.scale(0.0125F);
        this.temperature(1.6F);
        this.downfall(0.2F);

        this.precipitation(Biome.Precipitation.RAIN);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        //DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

//         this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .repeat(4)
                        .spreadHorizontally()
                        .decorate(new Spread32Decorator()));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                EcotonesConfiguredFeature.wrap(Feature.ORE, (new OreFeatureConfig(new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD), Blocks.IRON_ORE.getDefaultState(), 9)))
                .repeat(30)
                .spreadHorizontally()
                .uniformRange(YOffset.fixed(0), YOffset.fixed(64)));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                EcotonesConfiguredFeature.wrap(Feature.ORE, (new OreFeatureConfig(new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD), Blocks.GOLD_ORE.getDefaultState(), 9)))
                .repeat(6)
                .spreadHorizontally()
                .uniformRange(YOffset.fixed(0), YOffset.fixed(32)));

//        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.LAVA.getDefaultState()))
//                        .decorate(Decorator.LAVA_LAKE.configure(new ChanceDecoratorConfig(10))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.35))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.35))));

//        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
//                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.STONE.getDefaultState(), 2))
//                        .applyChance(5)
//                        .spreadHorizontally()
//                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

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
