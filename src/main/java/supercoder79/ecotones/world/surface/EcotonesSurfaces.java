package supercoder79.ecotones.world.surface;

import net.minecraft.block.Blocks;
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
    public static SurfaceBuilder<TernarySurfaceConfig> MESA;
    public static SurfaceBuilder<TernarySurfaceConfig> WHITE_MESA;
    public static SurfaceBuilder<TernarySurfaceConfig> ABOVE_Y;

    public static void init() {
        DESERT_SCRUB_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "desert_scrub"), new DesertScrubSurfaceBuilder(TernarySurfaceConfig.CODEC));
        PEAT_SWAMP_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "peat_swamp"), new PeatSwampSurfaceBuilder(TernarySurfaceConfig.CODEC));
        VOLCANIC_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "volcanic"), new VolcanicSurfaceBuilder(TernarySurfaceConfig.CODEC));
        SUPERVOLCANIC_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "supervolcanic"), new SuperVolcanicSurfaceBuilder(TernarySurfaceConfig.CODEC));
        HOT_SPRINGS_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "hot_springs"), new HotSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC));
        BLESSED_SPRINGS_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "blessed_springs"), new BlessedSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC));
        DELETE_WATER_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "delete_water"), new DeleteWaterSurfaceBuilder(TernarySurfaceConfig.CODEC));
        GREEN_SPIRES_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "green_spires"), new GreenSpiresSurfaceBuilder(TernarySurfaceConfig.CODEC));
        ULURU_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "uluru"), new UluruSurfaceBuilder(TernarySurfaceConfig.CODEC));
        WASTELAND_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "wasteland"), new WastelandSurfaceBuilder(TernarySurfaceConfig.CODEC));
        GRASS_MOUNTAIN = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "grass_mountain"), new GrassMountainSurfaceBuilder(TernarySurfaceConfig.CODEC));
        DRY_STEPPE = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "dry_steppe"), new DrySteppeSurfaceBuilder(TernarySurfaceConfig.CODEC));
        SHIELD = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "shield"), new ShieldSurfaceBuilder(TernarySurfaceConfig.CODEC));
        BEACH = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "beach"), new BeachSurfaceBuilder(TernarySurfaceConfig.CODEC, 66));
        MESA = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "mesa"), new MesaSurfaceBuilder(TernarySurfaceConfig.CODEC, y -> y < 78, MesaHelper::initializeRegularMesa, Blocks.TERRACOTTA.getDefaultState()));
        WHITE_MESA = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "white_mesa"), new MesaSurfaceBuilder(TernarySurfaceConfig.CODEC, y -> y < 72 || y > 88, MesaHelper::initializeWhiteMesa, Blocks.WHITE_TERRACOTTA.getDefaultState()));
        ABOVE_Y = Registry.register(Registry.SURFACE_BUILDER, new Identifier("ecotones", "above_y"), new StoneAboveYSurfaceBuilder(TernarySurfaceConfig.CODEC));
    }
}
