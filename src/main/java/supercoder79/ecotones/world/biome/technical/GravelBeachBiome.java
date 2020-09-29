package supercoder79.ecotones.world.biome.technical;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.client.sound.Sounds;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class GravelBeachBiome extends EcotonesBiome {
    public static GravelBeachBiome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "gravel_beach"), new GravelBeachBiome());
    }

    protected GravelBeachBiome() {
        super(new Settings()
                .configureSurfaceBuilder(EcotonesSurfaces.BEACH, SurfaceBuilder.GRAVEL_CONFIG)
                .precipitation(Precipitation.RAIN)
                .category(Category.BEACH)
                .depth(-0.025F)
                .scale(-0.07F)
                .temperature(0.9F)
                .downfall(0.9F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .loopSound(Sounds.BEACH_LOOP).build())
                .parent(null)
                .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                0.05,
                1.75);

        this.addStructureFeature(DefaultBiomeFeatures.NORMAL_MINESHAFT);
        this.addStructureFeature(DefaultBiomeFeatures.BURIED_TREASURE);
        this.addStructureFeature(DefaultBiomeFeatures.BEACHED_SHIPWRECK);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.method_28440(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(24))));

        this.addSpawn(SpawnGroup.CREATURE, new SpawnEntry(EntityType.TURTLE, 5, 2, 5));
        this.addSpawn(SpawnGroup.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
