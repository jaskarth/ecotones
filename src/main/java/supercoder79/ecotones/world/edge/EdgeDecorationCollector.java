package supercoder79.ecotones.world.edge;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.PlacedFeature;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class EdgeDecorationCollector {
    private final Map<RegistryKey<Biome>, List<Entry>> features = new HashMap<>();

    public void add(Biome biome, Function<Double, EcotonesConfiguredFeature<?, ?>> feature) {
        RegistryKey<Biome> key = BiomeRegistries.key(biome);
        features.computeIfAbsent(key, k -> new ArrayList<>())
                .add(new Entry(feature));
    }

    public Map<RegistryKey<Biome>, List<Entry>> features() {
        return features;
    }

    public static record Entry(Function<Double, EcotonesConfiguredFeature<?, ?>> feature) {

    }
}
