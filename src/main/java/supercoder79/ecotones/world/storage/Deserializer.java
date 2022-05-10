package supercoder79.ecotones.world.storage;

import net.minecraft.nbt.NbtCompound;

// Nbt -> Obj
public interface Deserializer<T> {
    T deserialize(NbtCompound nbt);
}
