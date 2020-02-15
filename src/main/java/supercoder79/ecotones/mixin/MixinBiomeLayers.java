package supercoder79.ecotones.mixin;

import net.minecraft.world.biome.layer.*;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.layers.generation.*;
import supercoder79.ecotones.layers.util.*;

import java.util.function.LongFunction;

@Mixin(BiomeLayers.class)
public abstract class MixinBiomeLayers {
    private static long seed = 0;

    @Shadow
    protected static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
        return null;
    }

    /**
     * @author SuperCoder79
     */
    @Overwrite
    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LevelGeneratorType generatorType, OverworldChunkGeneratorConfig settings, LongFunction<C> contextProvider) {
        //Initialize land
        LayerFactory<T> layerFactory = LandLayer.INSTANCE.create(contextProvider.apply(1L));

        layerFactory = ScaleLayer.FUZZY.create(contextProvider.apply(2000L), layerFactory);
        layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(1L), layerFactory);
        layerFactory = ScaleLayer.NORMAL.create(contextProvider.apply(2001L), layerFactory);

        layerFactory = stack(2601L, IncreaseEdgeCurvatureLayer.INSTANCE, layerFactory, 4, contextProvider);
        layerFactory = AddIslandLayer.INSTANCE.create(contextProvider.apply(2L), layerFactory);
        layerFactory = AddIslandLayer.INSTANCE.create(contextProvider.apply(6L), layerFactory);

        //scale up the land to be bigger
        layerFactory = stack(2001L, ScaleLayer.NORMAL, layerFactory, 9, contextProvider);

        //Add our biomes
        LayerFactory<T> biomeLayer = ClimateLayers.INSTANCE.create(contextProvider.apply(2L), seed + 79);
        biomeLayer = MountainLayer.INSTANCE.create(contextProvider.apply(49L), biomeLayer, seed + 1337);
        biomeLayer = DrainageLayer.INSTANCE.create(contextProvider.apply(4L), biomeLayer, seed + 97);
        //TODO: super rare volcanic and hot springs biomes that are really big (initialized in the regular biome layer)

        biomeLayer = stack(7979L, ScaleLayer.NORMAL, biomeLayer, 7, contextProvider);

        //Initialize special biomes (smaller biomes with c o o l effects)
        LayerFactory<T> specialBiomesLayer = PlainsOnlyLayer.INSTANCE.create(contextProvider.apply(3L));
        specialBiomesLayer = BaseSpecialBiomesLayer.INSTANCE.create(contextProvider.apply(32L), specialBiomesLayer);
        specialBiomesLayer = VolcanismLayer.INSTANCE.create(contextProvider.apply(24L), specialBiomesLayer, seed - 30);

        specialBiomesLayer = stack(3043L, ScaleLayer.NORMAL, specialBiomesLayer, 5, contextProvider);

        specialBiomesLayer = BiomeEdgeLayer.INSTANCE.create(contextProvider.apply(32L), specialBiomesLayer);
        specialBiomesLayer = BiomeEdgeEnsureLayer.INSTANCE.create(contextProvider.apply(37L), specialBiomesLayer);

        specialBiomesLayer = stack(303L, ScaleLayer.NORMAL, specialBiomesLayer, 0, contextProvider);

        //Merge special biomes with the regular biome layer
        biomeLayer = SpecialBiomeMerger.INSTANCE.create(contextProvider.apply(65L), biomeLayer, specialBiomesLayer);

        //Merge biomes onto the continent
        layerFactory = BiomeMerger.INSTANCE.create(contextProvider.apply(84L), layerFactory, biomeLayer);

        layerFactory = ShorelineLayer.INSTANCE.create(contextProvider.apply(54L), layerFactory);
        layerFactory = stack(2001L, ExpandShorelineLayer.INSTANCE, layerFactory, 7, contextProvider);

        //Add ocean temperatures
        LayerFactory<T> layerFactory2 = OceanTemperatureLayer.INSTANCE.create(contextProvider.apply(2L));
        layerFactory2 = stack(2001L, ScaleLayer.NORMAL, layerFactory2, 6, contextProvider);
        layerFactory = ApplyOceanTemperatureLayer.INSTANCE.create(contextProvider.apply(100L), layerFactory, layerFactory2);
        return layerFactory;
    }

    @Inject(method = "build(JLnet/minecraft/world/level/LevelGeneratorType;Lnet/minecraft/world/gen/chunk/OverworldChunkGeneratorConfig;)Lnet/minecraft/world/biome/source/BiomeLayerSampler;", at = @At("HEAD"))
    private static void captureSeed(long seedIn, LevelGeneratorType type, OverworldChunkGeneratorConfig config, CallbackInfoReturnable<BiomeLayerSampler> info) {
        seed = seedIn;
    }
}
