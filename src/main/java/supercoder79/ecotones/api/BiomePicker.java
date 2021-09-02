package supercoder79.ecotones.api;

import com.google.common.collect.Lists;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;

import java.util.List;
import java.util.Optional;

public final class BiomePicker {
    private final List<Entry> biomeEntries = Lists.newArrayList();
    private double totalWeight;
    public Object owner = "owner not set";

    public Integer choose(LayerRandomnessSource rand) {
        if (this.biomeEntries.size() == 0) {
            throw new UnsupportedOperationException("No biomes registered for picker of owner (" + this + ")!!! This is a problem!");
        }

        double randVal = target(rand);
        int i = -1;

        while (randVal >= 0) {
            ++i;
            randVal -= this.biomeEntries.get(i).weight;
        }

        Biome biome = Ecotones.REGISTRY.get(this.biomeEntries.get(i).biome);

        int id = Ecotones.REGISTRY.getRawId(biome);

        return id;
    }

    public void add(Biome biome, double weight) {
        this.biomeEntries.add(new Entry(biome, weight));
        this.totalWeight += weight;
    }

    private double target(LayerRandomnessSource random) {
        return (double) random.nextInt(Integer.MAX_VALUE) * this.totalWeight / Integer.MAX_VALUE;
    }

    public List<Entry> getBiomeEntries() {
        return biomeEntries;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public static class Entry {
        private final RegistryKey<Biome> biome;
        private final double weight;
        private Entry(Biome biome, double weight) {
            // Attempt from builtin
            Optional<RegistryKey<Biome>> optional = BuiltinRegistries.BIOME.getKey(biome);

            // Mod compat mode: use dynamic registry
            this.biome = optional.orElseGet(() -> Ecotones.REGISTRY.getKey(biome).get());
            this.weight = weight;
        }

        public RegistryKey<Biome> getBiome() {
            return biome;
        }

        public double getWeight() {
            return weight;
        }
    }
}