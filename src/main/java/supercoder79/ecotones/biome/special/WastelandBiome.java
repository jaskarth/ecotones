package supercoder79.ecotones.biome.special;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.biome.EcotonesBiome;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.FeatureConfigHolder;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.surface.EcotonesSurfaces;

public class WastelandBiome extends EcotonesBiome {
    private static final BranchedTreeFeatureConfig WASTELAND_TREE = new BranchedTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
            new BlobFoliagePlacer(0, 0, 0, 0, 0),
            new StraightTrunkPlacer(0, 0, 0)).build();

    public static WastelandBiome INSTANCE;
    public static WastelandBiome THICKET;
    public static WastelandBiome FLATS;
    public static WastelandBiome HILLS;
    public static WastelandBiome SHRUB;
    public static WastelandBiome DEPTHS;

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "wasteland"), new WastelandBiome(0.2f, 0.65f, 0, 8, 1.25));
        THICKET = Registry.register(Registry.BIOME, new Identifier("ecotones", "wasteland_thicket"), new WastelandBiome(0.8f, 0.65f, 0.1f, 4, 1.25));
        FLATS = Registry.register(Registry.BIOME, new Identifier("ecotones", "wasteland_flats"), new WastelandBiome(0.3f, 0.65f, 0, 0.1, 1.5));
        HILLS = Registry.register(Registry.BIOME, new Identifier("ecotones", "wasteland_hills"), new WastelandBiome(0.25f, 1.25f, 0.45f, 12, 0.7));
        SHRUB = Registry.register(Registry.BIOME, new Identifier("ecotones", "wasteland_shrub"), new WastelandBiome(0, 0.65f, 0, 6, 1.25));
        DEPTHS = Registry.register(Registry.BIOME, new Identifier("ecotones", "wasteland_depths"), new WastelandBiome(0.1f, -0.25f, 0, 2, 1.25));

        BiomeRegistries.registerAllSpecial(id ->
                Registry.BIOME.get(id).getName().asString().contains("desert") || Registry.BIOME.get(id).getName().asString().contains("scrub"),
                Registry.BIOME.getRawId(INSTANCE), Registry.BIOME.getRawId(THICKET), Registry.BIOME.getRawId(FLATS), Registry.BIOME.getRawId(HILLS), Registry.BIOME.getRawId(SHRUB), Registry.BIOME.getRawId(DEPTHS));

        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 150);
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 2);
        BiomeRegistries.registerBiomeVariants(INSTANCE, THICKET, FLATS, HILLS, SHRUB, DEPTHS);
    }


    protected WastelandBiome(float treeChance, float depth, float scale, double hilliness, double volatility) {
        super((new Settings())
                .configureSurfaceBuilder(EcotonesSurfaces.WASTELAND_BUILDER, new TernarySurfaceConfig(EcotonesBlocks.driedDirtBlock.getDefaultState(), EcotonesBlocks.driedDirtBlock.getDefaultState(), SurfaceBuilder.GRAVEL))
                .precipitation(Precipitation.NONE)
                .category(Category.DESERT)
                .depth(depth)
                .scale(scale)
                .temperature(1.7F)
                .downfall(0.2F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x947c26)
                        .waterFogColor(0x947c26)
                        .fogColor(0xc6e4f5)
                        .build()).parent(null)
                .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                hilliness,
                volatility);

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.75f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.25f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 1, 2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.FANCY_TREE.configure(WASTELAND_TREE)
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, treeChance, 1))));

        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
    }

    @Override
    public int getSkyColor() {
        return 0xf5dd89;
    }

    @Override
    public int getGrassColorAt(double x, double z) {
        return 0x705719;
    }

    @Override
    public int getFoliageColor() {
        return 0x705719;
    }
}
