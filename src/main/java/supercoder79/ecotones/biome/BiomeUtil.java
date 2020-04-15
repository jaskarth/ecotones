package supercoder79.ecotones.biome;

import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.layers.generation.MountainLayer;

public class BiomeUtil {
    public static Biome register(Identifier name, EcotonesBiome.Builder builder) {
        Integer[] ids = new Integer[2];
        //should be a safe cast
        EcotonesBiome ret = (EcotonesBiome) Registry.register(Registry.BIOME, name, builder.build());
        ids[0] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
                new Identifier(name.getNamespace(), name.getPath().concat("_hilly")),
                builder
                        .depth(ret.getDepth() + 0.75f)
                        .scale(ret.getScale() + 0.15f)
                        .temperature(ret.getTemperature() -0.1f)
                        .hilliness(ret.getHilliness() + 0.8)
                        .volatility(ret.getVolatility() - 0.35)
                        .build()));

        ids[1] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
                new Identifier(name.getNamespace(), name.getPath().concat("_mountainous")),
                builder
                        .depth(ret.getDepth() + 1.5f)
                        .scale(ret.getScale() + 0.45f)
                        .temperature(ret.getTemperature() - 0.2f)
                        .hilliness(ret.getHilliness() + 2.6)
                        .volatility(ret.getVolatility() - 0.45)
                        .build()));

        MountainLayer.Biome2MountainBiomeMap.put(Registry.BIOME.getRawId(ret), ids);

        FabricBiomes.addSpawnBiome(ret);
        return ret;
    }

    public static void addDefaultSpawns(EcotonesBiome biome) {
        biome.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        biome.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, 10, 4, 4));
        biome.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        biome.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, 8, 4, 4));
        biome.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        biome.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
