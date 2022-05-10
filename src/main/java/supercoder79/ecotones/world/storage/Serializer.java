package supercoder79.ecotones.world.storage;

import net.minecraft.nbt.NbtCompound;

// Obj -> Nbt
public interface Serializer<T> {
    // When serializing, we only have an Object context with StorageKey<?>. This allows us to do cursed generics ninja mechanics to make the serialize() impl method properly defined
    default NbtCompound serializeRaw(Object obj, NbtCompound nbt) {
        return serialize((T) obj, nbt);
    }

    NbtCompound serialize(T obj, NbtCompound nbt);
}
