package supercoder79.ecotones.world.storage;

import supercoder79.ecotones.world.storage.data.RiverData;

import java.util.HashMap;
import java.util.Map;

// Storage keys main data holder
public final class StorageKeys {
    private static final Map<String, StorageKey<?>> KEYS = new HashMap<>();

    public static StorageKey<RiverData> RIVER_DATA = register(new StorageKey<>("river_data", RiverData::serialize, RiverData::deserialize));

    private static <T> StorageKey<T> register(StorageKey<T> key) {
        KEYS.put(key.id(), key);

        return key;
    }

    public static StorageKey<?> get(String id) {
        return KEYS.get(id);
    }
}
