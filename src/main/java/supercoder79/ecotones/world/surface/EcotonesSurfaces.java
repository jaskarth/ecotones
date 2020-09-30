package supercoder79.ecotones.world.surface;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class EcotonesSurfaces {
    public static SurfaceBuilder<TernarySurfaceConfig> DESERT_SCRUB_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> PEAT_SWAMP_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> VOLCANIC_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> SUPERVOLCANIC_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> HOT_SPRINGS_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> BLESSED_SPRINGS_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> DELETE_WATER_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> GREEN_SPIRES_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> ULURU_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> WASTELAND_BUILDER;
    public static SurfaceBuilder<TernarySurfaceConfig> GRASS_MOUNTAIN;
    public static SurfaceBuilder<TernarySurfaceConfig> DRY_STEPPE;
    public static SurfaceBuilder<TernarySurfaceConfig> SHIELD;
    public static SurfaceBuilder<TernarySurfaceConfig> BEACH;

    public static void init() {
        DESERT_SCRUB_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "desert_scrub_builder"), new DesertScrubSurfaceBuilder(TernarySurfaceConfig.CODEC));
        PEAT_SWAMP_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "peat_swamp_builder"), new PeatSwampSurfaceBuilder(TernarySurfaceConfig.CODEC));
        VOLCANIC_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "volcanic_builder"), new VolcanicSurfaceBuilder(TernarySurfaceConfig.CODEC));
        SUPERVOLCANIC_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "supervolcanic_builder"), new SuperVolcanicSurfaceBuilder(TernarySurfaceConfig.CODEC));
        HOT_SPRINGS_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "hot_springs_builder"), new HotSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC));
        BLESSED_SPRINGS_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "blessed_springs_builder"), new BlessedSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC));
        DELETE_WATER_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "delete_water_builder"), new DeleteWaterSurfaceBuilder(TernarySurfaceConfig.CODEC));
        GREEN_SPIRES_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "green_spires_builder"), new GreenSpiresSurfaceBuilder(TernarySurfaceConfig.CODEC));
        ULURU_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "uluru_builder"), new UluruSurfaceBuilder(TernarySurfaceConfig.CODEC));
        WASTELAND_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "wasteland_builder"), new WastelandSurfaceBuilder(TernarySurfaceConfig.CODEC));
        GRASS_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "grass_mountain_builder"), new GrassMountainSurfaceBuilder(TernarySurfaceConfig.CODEC));
        DRY_STEPPE = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "dry_steppe_builder"), new DrySteppeSurfaceBuilder(TernarySurfaceConfig.CODEC));
        SHIELD = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "shield"), new ShieldSurfaceBuilder(TernarySurfaceConfig.CODEC));
        BEACH = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "beach"), new BeachSurfaceBuilder(TernarySurfaceConfig.CODEC, 66));
    }
}
