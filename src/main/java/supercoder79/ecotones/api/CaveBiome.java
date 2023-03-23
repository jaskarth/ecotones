package supercoder79.ecotones.api;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.ArrayList;
import java.util.List;

public abstract class CaveBiome {
    public static final CaveBiome DEFAULT = new CaveBiome() {};

    private final List<PlacedFeature> features = new ArrayList<>();

    protected void addFeature(PlacedFeature feature) {
        this.features.add(feature);
    }

    public List<PlacedFeature> getFeatures() {
        return this.features;
    }
}
