package supercoder79.ecotones.api;

import com.google.common.collect.Lists;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;

import java.util.*;

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

    public static final Climate[] VALUES = Arrays.copyOf(values(), values().length);

    final Map<ClimateType, BiomePicker> pickers = new HashMap<>();

    Climate() {
        for (ClimateType value : ClimateType.values()) {
            BiomePicker picker = new BiomePicker();
            picker.owner = this + " | " + value; // So that it shows up in crashes as "CLIMATE | TYPE"
            this.pickers.put(value, picker);
        }
    }

    public Integer choose(LayerRandomnessSource rand) {
        return pickerFor(ClimateType.REGULAR).choose(rand);
    }

    public void add(Biome biome, double weight) {
        add(ClimateType.REGULAR, biome, weight);
    }

    public void add(ClimateType type, Biome biome, double weight) {
        pickerFor(type).add(biome, weight);
    }

    public BiomePicker pickerFor(ClimateType type) {
        return this.pickers.get(type);
    }
}
