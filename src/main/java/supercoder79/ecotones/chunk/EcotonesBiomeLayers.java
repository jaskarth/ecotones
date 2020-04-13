package supercoder79.ecotones.chunk;

import net.minecraft.world.biome.layer.*;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import supercoder79.ecotones.layers.generation.*;
import supercoder79.ecotones.layers.util.*;

import java.util.function.LongFunction;

public class EcotonesBiomeLayers {
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
        LayerFactory<T> layerFactory = parent;

        for(int i = 0; i < count; ++i) {
            layerFactory = layer.create(contextProvider.apply(seed + i), layerFactory);
        }

        return layerFactory;
    }

    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(long seed, LongFunction<C> contextProvider) {
        //Initialize land
        LayerFactory<T> layerFactory = LandLayer.INSTANCE.create(contextProvider.apply(1L));

        layerFactory = ScaleLayer.FUZZY.create(contextProvider.apply(2000L), layerFactory);
        layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(1L), layerFactory);
        layerFactory = ScaleLayer.NORMAL.create(contextProvider.apply(2001L), layerFactory);

        layerFactory = stack(2601L, IncreaseEdgeCurvatureLayer.INSTANCE, layerFactory, 4, contextProvider);
        layerFactory = AddIslandLayer.INSTANCE.create(contextProvider.apply(2L), layerFactory);

        //scale up the land to be bigger
        layerFactory = stack(2001L, ScaleLayer.NORMAL, layerFactory, 9, contextProvider);

        //Add our biomes
        LayerFactory<T> biomeLayer = ClimateLayers.INSTANCE.create(contextProvider.apply(2L), seed + 79);
        biomeLayer = MountainLayer.INSTANCE.create(contextProvider.apply(49L), biomeLayer, seed + 1337);
        biomeLayer = DrainageLayer.INSTANCE.create(contextProvider.apply(4L), biomeLayer, seed + 97);

        biomeLayer = stack(7979L, ScaleLayer.NORMAL, biomeLayer, 7, contextProvider);

        //Initialize special biomes (smaller biomes with c o o l effects)
        LayerFactory<T> specialBiomesLayer = PlainsOnlyLayer.INSTANCE.create(contextProvider.apply(3L));

        specialBiomesLayer = BigSpecialBiomesLayer.INSTANCE.create(contextProvider.apply(38L), specialBiomesLayer);
        specialBiomesLayer = stack(3043L, ScaleLayer.NORMAL, specialBiomesLayer, 2, contextProvider);

        specialBiomesLayer = BaseSpecialBiomesLayer.INSTANCE.create(contextProvider.apply(32L), specialBiomesLayer);
        specialBiomesLayer = BiomeVariantLayer.INSTANCE.create(contextProvider.apply(632L), specialBiomesLayer);
        specialBiomesLayer = VolcanismLayer.INSTANCE.create(contextProvider.apply(24L), specialBiomesLayer, seed - 30);

        specialBiomesLayer = stack(3043L, ScaleLayer.NORMAL, specialBiomesLayer, 5, contextProvider);

        specialBiomesLayer = BiomeEdgeLayer.INSTANCE.create(contextProvider.apply(36L), specialBiomesLayer);
        specialBiomesLayer = BiomeEdgeEnsureLayer.INSTANCE.create(contextProvider.apply(37L), specialBiomesLayer);

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

    public static BiomeLayerSampler build(long seed) {
        LayerFactory<CachingLayerSampler> layerFactory = build(seed, (salt) -> new CachingLayerContext(25, seed, salt));
        return new BiomeLayerSampler(layerFactory);
    }
}
