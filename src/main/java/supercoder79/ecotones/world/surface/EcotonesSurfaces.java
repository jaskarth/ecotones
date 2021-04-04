package supercoder79.ecotones.world.surface;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesSurfaces {
    public static final SurfaceBuilder<TernarySurfaceConfig> DESERT_SCRUB_BUILDER = new DesertScrubSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> PEAT_SWAMP_BUILDER = new PeatSwampSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> VOLCANIC_BUILDER = new VolcanicSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> SUPERVOLCANIC_BUILDER = new SuperVolcanicSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> HOT_SPRINGS_BUILDER = new HotSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> BLESSED_SPRINGS_BUILDER = new BlessedSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> DELETE_WATER_BUILDER = new DeleteWaterSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GREEN_SPIRES_BUILDER = new GreenSpiresSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> ULURU_BUILDER = new UluruSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> WASTELAND_BUILDER = new WastelandSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GRASS_MOUNTAIN = new GrassMountainSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> DRY_STEPPE = new DrySteppeSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> SHIELD = new ShieldSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> BEACH = new BeachSurfaceBuilder(TernarySurfaceConfig.CODEC, 66);
    public static final SurfaceBuilder<TernarySurfaceConfig> MESA = new MesaSurfaceBuilder(TernarySurfaceConfig.CODEC, y -> y < 78, MesaHelper::initializeRegularMesa, Blocks.TERRACOTTA.getDefaultState());
    public static final SurfaceBuilder<TernarySurfaceConfig> WHITE_MESA = new MesaSurfaceBuilder(TernarySurfaceConfig.CODEC, y -> y < 72 || y > 88, MesaHelper::initializeWhiteMesa, Blocks.WHITE_TERRACOTTA.getDefaultState());
    public static final SurfaceBuilder<TernarySurfaceConfig> ABOVE_Y = new StoneAboveYSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GRAVEL_BANDS = new GravelBandsSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GRANITE_SPRINGS = new GraniteSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC);

    public static void init() {
        register("desert_scrub", DESERT_SCRUB_BUILDER);
        register("peat_swamp", PEAT_SWAMP_BUILDER);
        register("volcanic", VOLCANIC_BUILDER);
        register("supervolcanic", SUPERVOLCANIC_BUILDER);
        register("hot_springs", HOT_SPRINGS_BUILDER);
        register("blessed_springs", BLESSED_SPRINGS_BUILDER);
        register("delete_water", DELETE_WATER_BUILDER);
        register("green_spires", GREEN_SPIRES_BUILDER);
        register("uluru", ULURU_BUILDER);
        register("wasteland", WASTELAND_BUILDER);
        register("grass_mountain", GRASS_MOUNTAIN);
        register("dry_steppe", DRY_STEPPE);
        register("shield", SHIELD);
        register("beach", BEACH);
        register("mesa", MESA);
        register("white_mesa", WHITE_MESA);
        register("above_y", ABOVE_Y);
        register("gravel_bands", GRAVEL_BANDS);
        register("granite_springs", GRANITE_SPRINGS);
    }

    private static void register(String name, SurfaceBuilder<?> builder) {
        Registry.register(Registry.SURFACE_BUILDER, Ecotones.id(name), builder);
        RegistryReport.increment("Surface Builder");
    }
}
