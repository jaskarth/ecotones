package com.jaskarth.ecotones.world.worldgen.river.deco;

import net.minecraft.world.gen.feature.PlacedFeature;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public final class RiverDecorationCollector {
    private final List<Entry> features = new ArrayList<>();

    public void add(EcotonesConfiguredFeature<?, ?> feature) {
        features.add(new Entry(feature.placed(), 0, false));
    }

    public void add(EcotonesConfiguredFeature<?, ?> feature, boolean needsOpenToAir) {
        features.add(new Entry(feature.placed(), 0, needsOpenToAir));
    }

    public void add(EcotonesConfiguredFeature<?, ?> feature, boolean needsOpenToAir, int minimumDist) {
        features.add(new Entry(feature.placed(), minimumDist, needsOpenToAir));
    }

    public List<Entry> features() {
        return features;
    }

    public record Entry(PlacedFeature feature, int minimumDist, boolean needsOpenToAir) {

    }
}
