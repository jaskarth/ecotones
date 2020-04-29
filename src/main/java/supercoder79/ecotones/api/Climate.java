package supercoder79.ecotones.api;

import com.google.common.collect.Lists;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

import java.util.List;

public enum Climate {
    HOT_DESERT,
    HOT_VERY_DRY,
    HOT_DRY,
    HOT_MODERATE,
    HOT_MILD,
    HOT_HUMID,
    HOT_VERY_HUMID,
    HOT_RAINFOREST,

    WARM_DESERT,
    WARM_VERY_DRY,
    WARM_DRY,
    WARM_MODERATE,
    WARM_MILD,
    WARM_HUMID,
    WARM_VERY_HUMID,
    WARM_RAINFOREST,

    COOL_DESERT,
    COOL_VERY_DRY,
    COOL_DRY,
    COOL_MODERATE,
    COOL_MILD,
    COOL_HUMID,
    COOL_VERY_HUMID,
    COOL_RAINFOREST,

    COLD_DESERT,
    COLD_VERY_DRY,
    COLD_DRY,
    COLD_MODERATE,
    COLD_MILD,
    COLD_HUMID,
    COLD_VERY_HUMID,
    COLD_RAINFOREST;

    private final List<Entry> biomeEntries = Lists.newArrayList();
    private double weightTotal;

    Climate() {

    }

    public Integer choose(LayerRandomnessSource rand) {
        if (biomeEntries.size() == 0) {
            throw new UnsupportedOperationException("No biomes registered for climate " + this + "!!! This is a problem!");
        }

        //valocode boogaloo
        double randVal = target(rand);
        int i = -1;

        while (randVal >= 0) {
            ++i;
            randVal -= biomeEntries.get(i).weight;
        }
        return Registry.BIOME.getRawId(biomeEntries.get(i).getBiome());
    }

    public void add(Biome biome, double weight) {
        this.biomeEntries.add(new Entry(biome, weight));
        weightTotal += weight;
    }

    private double target(LayerRandomnessSource random) {
        return (double) random.nextInt(Integer.MAX_VALUE) * weightTotal / Integer.MAX_VALUE;
    }

    private static class Entry {
        private final Biome biome;
        private final double weight;
        private Entry(Biome biome, double weight) {
            this.biome = biome;
            this.weight = weight;
        }

        private Biome getBiome() {
            return biome;
        }
    }
}
