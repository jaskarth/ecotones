package com.jaskarth.ecotones.util;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.biome.EarlyBiomeRegistry;

public final class BiomeChecker {
    public static void check(Biome biome) {
        Identifier id = EarlyBiomeRegistry.getId(biome);
        boolean hasSpawns = false;
        for (SpawnGroup value : SpawnGroup.values()) {
            if (!biome.getSpawnSettings().getSpawnEntries(value).getEntries().isEmpty()) {
                hasSpawns = true;
            }
        }

        if (!hasSpawns) {
            System.out.println("Biome [" + id + "] has no spawns");
        }

//        if (!biome.getGenerationSettings().hasStructureFeature(StructureFeature.STRONGHOLD)) {
//            System.out.println("Biome [" + id + "] has no stronghold");
//        }
    }
}
