package supercoder79.ecotones.api;

import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public abstract class CaveBiome {
    public static final CaveBiome DEFAULT = new CaveBiome() {};

    private final List<ConfiguredFeature<?, ?>> features = new ArrayList<>();

    protected void addFeature(ConfiguredFeature<?, ?> feature) {
        this.features.add(feature);
    }

    public List<ConfiguredFeature<?, ?>> getFeatures() {
        return this.features;
    }
}
