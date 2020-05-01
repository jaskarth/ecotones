package supercoder79.ecotones.world.layers.seed;

import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface SeedInitLayer {
    <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, long seed);
}
