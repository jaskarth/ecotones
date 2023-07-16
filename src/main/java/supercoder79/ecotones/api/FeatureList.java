package supercoder79.ecotones.api;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureList {
    private final Map<GenerationStep.Feature, List<PlacedFeature>> features = new HashMap<>();
    public FeatureList() {

    }

    public void add(GenerationStep.Feature step, PlacedFeature feature) {
        if (feature == null) {
            throw new IllegalStateException("Feature list encountered null feature!");
        }

        this.features.computeIfAbsent(step, k -> new ArrayList<>()).add(feature);
    }

    public List<PlacedFeature> get(GenerationStep.Feature step) {
        return this.features.getOrDefault(step, new ArrayList<>());
    }
}
