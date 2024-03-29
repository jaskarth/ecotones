package supercoder79.ecotones.world.biome;

import com.google.common.collect.HashBiMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class EarlyBiomeRegistry {
    private static final HashBiMap<Identifier, Biome> IDS = HashBiMap.create();
    private static final HashBiMap<RegistryKey<Biome>, Biome> KEYS = HashBiMap.create();
    public static Biome register(Identifier loc, Biome biome) {
        IDS.put(loc, biome);
        KEYS.put(RegistryKey.of(RegistryKeys.BIOME, loc), biome);

        return biome;
    }

    public static Set<Identifier> ids() {
        return IDS.keySet();
    }

    public static Biome get(Identifier loc) {
        return IDS.get(loc);
    }

    public static Identifier getId(Biome biome) {
        return IDS.inverse().get(biome);
    }

    public static Optional<RegistryKey<Biome>> get(Biome biome) {
        return Optional.ofNullable(KEYS.inverse().get(biome));
    }
}
