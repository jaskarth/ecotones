package supercoder79.ecotones.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.SulfurousLakeFeature;

public final class BiomeHelper {
    public static void addDefaultSpawns(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PIG, 10, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.COW, 8, 4, 4));
        
        builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.BAT, 10, 8, 8));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }

    public static void addTemperateSpawns(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
    }

    public static void addDefaultFeatures(EcotonesBiomeBuilder builder) {
        // Sulfurous lakes
        builder.addFeature(GenerationStep.Feature.LAKES, EcotonesFeatures.SULFUROUS_LAKE.configure(
                new SulfurousLakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(Blocks.STONE.getDefaultState()))
        ).decorateAll(
                RarityFilterPlacementModifier.of(5),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.fixed(0), YOffset.getTop())),
                EnvironmentScanPlacementModifier.of(
                        Direction.DOWN,
                        BlockPredicate.bothOf(BlockPredicate.not(BlockPredicate.IS_AIR), BlockPredicate.insideWorldBounds(new BlockPos(0, -5, 0))),
                        32
                ),
                SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5)
        ));

        // Phosphate domes
        builder.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.PHOSPHATE_DOME.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(220));

        builder.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.DRAINAGE.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.DRAINAGE_DECORATOR.configure()));
    }

    @Deprecated
    // Why I thought this was a good idea is beyond me. TODO remove
    public static boolean contains(int id, String name) {
        return Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(id)).get().toString().contains(name);
    }

    public static boolean isOcean(int id) {
        return id == BiomeIdManager.getId(BiomeKeys.WARM_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.COLD_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.DEEP_COLD_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.DEEP_FROZEN_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.DEEP_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.DEEP_LUKEWARM_OCEAN);
    }

    public static boolean isShallowOcean(int id) {
        return id == BiomeIdManager.getId(BiomeKeys.WARM_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.COLD_OCEAN) ||
                id == BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN);
    }

    // Dev and testing stuff

    // "Mom, can we have single biome worlds at home?"
    // The single biome worlds at home:
    public static void fillClimates(Biome biome) {
        for (Climate climate : Climate.values()) {
            climate.add(biome, 999);
        }
    }
}
