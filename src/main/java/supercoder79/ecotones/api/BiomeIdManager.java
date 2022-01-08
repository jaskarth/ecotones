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
    private static final BiMap<RegistryKey<Biome>, Integer> MAP = HashBiMap.create();
    private static final AtomicInteger ID = new AtomicInteger(0);

    public static void register(RegistryKey<Biome> key) {
        if (!MAP.containsKey(key)) {
            MAP.put(key, ID.getAndIncrement());
        }
    }

    public static int getId(RegistryKey<Biome> key) {
        return MAP.get(key);
    }

    public static RegistryKey<Biome> getKey(int id) {
        return MAP.inverse().get(id);
    }
}
