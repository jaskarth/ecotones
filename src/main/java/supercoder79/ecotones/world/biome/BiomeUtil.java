package supercoder79.ecotones.world.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;

public class BiomeUtil {
    public static final int WARM_OCEAN_ID = 44;
    public static final int LUKEWARM_OCEAN_ID = 45;
    public static final int OCEAN_ID = 0;
    public static final int COLD_OCEAN_ID = 46;
    public static final int FROZEN_OCEAN_ID = 10;
    public static final int DEEP_WARM_OCEAN_ID = 47;
    public static final int DEEP_LUKEWARM_OCEAN_ID = 48;
    public static final int DEEP_OCEAN_ID = 24;
    public static final int DEEP_COLD_OCEAN_ID = 49;
    public static final int DEEP_FROZEN_OCEAN_ID = 50;

//    public static Biome registerBase(Identifier name, EcotonesBiome.Builder builder) {
//        Integer[] ids = new Integer[2];
//        //should be a safe cast
//        EcotonesBiome ret = (EcotonesBiome) Registry.register(Registry.BIOME, name, builder.build());
//        ids[0] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
//                new Identifier(name.getNamespace(), name.getPath().concat("_hilly")),
//                builder
//                        .depth(ret.getDepth() + 0.5f)
//                        .scale(ret.getScale() + 0.15f)
//                        .temperature(ret.getTemperature() - 0.1f)
//                        .hilliness(ret.getHilliness() + 0.8)
//                        .volatility(ret.getVolatility() - 0.225)
//                        .build()));
//
//        ids[1] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
//                new Identifier(name.getNamespace(), name.getPath().concat("_mountainous")),
//                builder
//                        .depth(ret.getDepth() + 1.125f)
//                        .scale(ret.getScale() + 0.4f)
//                        .temperature(ret.getTemperature() - 0.2f)
//                        .hilliness(ret.getHilliness() + 2.6)
//                        .volatility(ret.getVolatility() - 0.35)
//                        .build()));
//
//        MountainLayer.BIOME_TO_MOUNTAINS.put(BuiltinRegistries.BIOME.getRawId(ret), ids);
//        return ret;
//    }

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
        builder.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.DRAINAGE.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.DRAINAGE_DECORATOR.configure(DecoratorConfig.DEFAULT)));
    }

    public static boolean contains(int id, String name) {
        return Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(id)).get().toString().contains(name);
    }

    public static boolean isOcean(int id) {
        return id == WARM_OCEAN_ID || id == LUKEWARM_OCEAN_ID || id == OCEAN_ID || id == COLD_OCEAN_ID || id == FROZEN_OCEAN_ID || id == DEEP_WARM_OCEAN_ID || id == DEEP_LUKEWARM_OCEAN_ID || id == DEEP_OCEAN_ID || id == DEEP_COLD_OCEAN_ID || id == DEEP_FROZEN_OCEAN_ID;
    }

    public static boolean isShallowOcean(int id) {
        return id == WARM_OCEAN_ID || id == LUKEWARM_OCEAN_ID || id == OCEAN_ID || id == COLD_OCEAN_ID || id == FROZEN_OCEAN_ID;
    }
}
