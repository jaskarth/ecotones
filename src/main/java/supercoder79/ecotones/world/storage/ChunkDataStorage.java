package supercoder79.ecotones.world.storage;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.HashMap;
import java.util.Map;

// Abstract holder for extra data storage in a chunk
public final class ChunkDataStorage {
    private final Map<StorageKey<?>, Object> storageMap = new HashMap<>();

    // Add with key
    public <T> void add(StorageKey<T> key, T obj) {
        this.storageMap.put(key, obj);
    }

    // Get with key
    public <T> T get(StorageKey<T> key) {
        return (T) this.storageMap.get(key);
    }

    public void serialize(NbtCompound nbt) {
        NbtList list = new NbtList();

        // Serialize all entities
        for (Map.Entry<StorageKey<?>, Object> entry : this.storageMap.entrySet()) {
            NbtCompound holder = new NbtCompound();
            holder.putString("Id", entry.getKey().id());
            holder.put("Obj", entry.getKey().serializer().serializeRaw(entry.getValue(), new NbtCompound()));
            list.add(holder);
        }

        nbt.put("Elements", list);
    }

    public static ChunkDataStorage deserialize(NbtCompound nbt) {
        ChunkDataStorage storage = new ChunkDataStorage();

        NbtList list = nbt.getList("Elements", NbtElement.COMPOUND_TYPE);

        for (NbtElement element : list) {
            NbtCompound compound = (NbtCompound) element;

            // Deserialize data
            String id = compound.getString("Id");
            StorageKey<?> key = StorageKeys.get(id);
            NbtCompound obj = compound.getCompound("Obj");

            // Direct map access to prevent generics oddities
            storage.storageMap.put(key, key.deserializer().deserialize(obj));
        }

        return storage;
    }
}