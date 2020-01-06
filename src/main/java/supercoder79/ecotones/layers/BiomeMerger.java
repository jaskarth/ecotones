package supercoder79.ecotones.layers;

import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public class BiomeMerger implements MergingLayer {
    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int landSample = sampler1.sample(x, z);
        int biomeSample = sampler2.sample(x, z);
        return landSample == 1 ? biomeSample : landSample;
    }

    @Override
    public int transformX(int x) {
        return x;
    }

    @Override
    public int transformZ(int z) {
        return z;
    }
}
