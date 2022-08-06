package supercoder79.ecotones.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BiomeIdManager {
    public static final BiMap<RegistryKey<Biome>, Integer> MAP = HashBiMap.create();

    public static void register(RegistryKey<Biome> key, int id) {
        if (!MAP.containsKey(key)) {
            MAP.put(key, id);
        }
    }

    public static void clear() {
        MAP.clear();
    }

    public static int getId(RegistryKey<Biome> key) {
        return MAP.get(key);
    }

    public static RegistryKey<Biome> getKey(int id) {
        return MAP.inverse().get(id);
    }
}
