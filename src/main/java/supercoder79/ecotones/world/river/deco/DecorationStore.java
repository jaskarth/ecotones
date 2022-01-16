package supercoder79.ecotones.world.river.deco;

import net.minecraft.world.gen.feature.PlacedFeature;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public final class DecorationStore {
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

    public static record Entry(PlacedFeature feature, int minimumDist, boolean needsOpenToAir) {

    }
}
