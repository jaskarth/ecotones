package supercoder79.ecotones.world.edge;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.river.deco.RiverDecorationCollector;
import supercoder79.ecotones.world.river.deco.RiverDecorator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeDecorator {
    private static final MultiNoiseUtil.MultiNoiseSampler SAMPLER = MultiNoiseUtil.createEmptyMultiNoiseSampler();

    public static EdgeDecorator EMPTY = new EdgeDecorator();

    private final EdgeDecorationCollector decorations = new EdgeDecorationCollector();

    public EdgeDecorationCollector getDecorations() {
        return decorations;
    }

    public void decorate(int chunkX, int chunkZ, BiomeSource source, FeatureContext<?> context) {
        if (this == EMPTY) {
            return;
        }

        Map<RegistryKey<Biome>, List<EdgeDecorationCollector.Entry>> features = decorations.features();

        Map<RegistryKey<Biome>, Double> weights = new HashMap<>();

        for (int x1 = -5; x1 <= 5; x1++) {
            for (int z1 = -5; z1 <= 5; z1++) {
                RegistryKey<Biome> key = source.getBiome((chunkX + x1) * 4 + 2, 0, (chunkZ + z1) * 4 + 2, SAMPLER).getKey().get();

                if (features.containsKey(key)) {
                    Double val = weights.getOrDefault(key, 0.0);

                    val += (1 / (11.0 * 11.0));

                    weights.put(key, val);
                }
            }
        }

        for (Map.Entry<RegistryKey<Biome>, List<EdgeDecorationCollector.Entry>> entry : features.entrySet()) {
            if (weights.containsKey(entry.getKey())) {

                double weight = weights.get(entry.getKey());

                for (EdgeDecorationCollector.Entry deco : entry.getValue()) {
                    deco
                            .feature()
                            .apply(weight)
                            .placed()
                            .generateUnregistered(context.getWorld(), context.getGenerator(), context.getRandom(), context.getOrigin());
                }
            }
        }
    }
}
