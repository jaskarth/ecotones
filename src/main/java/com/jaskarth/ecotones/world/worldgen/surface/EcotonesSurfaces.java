package com.jaskarth.ecotones.world.worldgen.surface;

import com.jaskarth.ecotones.world.worldgen.surface.system.MesaHelper;
import net.minecraft.block.Blocks;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.TernarySurfaceConfig;

public final class EcotonesSurfaces {
    public static final SurfaceBuilder<DesertScrubSurfaceBuilder.Config> DESERT_SCRUB_BUILDER = new DesertScrubSurfaceBuilder(DesertScrubSurfaceBuilder.Config.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> DELETE_WATER_BUILDER = new DeleteWaterSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GREEN_SPIRES_BUILDER = new GreenSpiresSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GRASS_MOUNTAIN = new GrassMountainSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> DRY_STEPPE = new DrySteppeSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> SHIELD = new ShieldSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> BEACH = new BeachSurfaceBuilder(TernarySurfaceConfig.CODEC, 66);
    public static final SurfaceBuilder<TernarySurfaceConfig> MESA = new MesaSurfaceBuilder(TernarySurfaceConfig.CODEC, y -> y < 78, MesaHelper::initializeRegularMesa, Blocks.TERRACOTTA.getDefaultState());
    public static final SurfaceBuilder<TernarySurfaceConfig> WHITE_MESA = new MesaSurfaceBuilder(TernarySurfaceConfig.CODEC, y -> y < 72 || y > 88, MesaHelper::initializeWhiteMesa, Blocks.WHITE_TERRACOTTA.getDefaultState());
    public static final SurfaceBuilder<TernarySurfaceConfig> ABOVE_Y = new StoneAboveYSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GRAVEL_BANDS = new GravelBandsSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> GRANITE_SPRINGS = new GraniteSpringsSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> STONE_SPLOTCHES = new StoneSplotchesSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> BIRCH_LAKES = new BirchLakesSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> COARSE_DIRT_BANDS = new CoarseDirtBandsSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> ROCKY_STEPPE = new RockySteppeSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> THORN_BRUSH = new ThornBrushSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> RED_ROCK = new RedRockSurfaceBuilder(TernarySurfaceConfig.CODEC);
    public static final SurfaceBuilder<TernarySurfaceConfig> SLOPED_STONE = new SlopedStoneSurfaceBuilder(TernarySurfaceConfig.CODEC);

    public static void init() {
    }
}
