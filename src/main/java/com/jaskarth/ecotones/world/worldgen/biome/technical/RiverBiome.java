package com.jaskarth.ecotones.world.worldgen.biome.technical;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import com.jaskarth.ecotones.world.worldgen.biome.BiomeHelper;
import com.jaskarth.ecotones.world.worldgen.biome.EarlyBiomeRegistry;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.world.worldgen.biome.EcotonesBiomeBuilder;

public class RiverBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("river", new RiverBiome());
        BiomeRegistries.registerNoBeachBiome(biome);
    }

    protected RiverBiome() {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(-0.5F);
        this.scale(-0.09F);
        this.temperature(0.9F);
        this.downfall(0.9F);

        this.precipitation(Biome.Precipitation.RAIN);

        
        BiomeHelper.addDefaultFeatures(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesConfiguredFeature.wrap(Feature.SEAGRASS, (new ProbabilityConfig(0.4f)))
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG))
                .spreadHorizontally()
                .repeat(48));

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
