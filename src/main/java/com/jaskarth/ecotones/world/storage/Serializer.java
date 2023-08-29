package com.jaskarth.ecotones.world.storage;

import net.minecraft.nbt.NbtCompound;

// Obj -> Nbt
public interface Serializer<T> {
    default NbtCompound serializeRaw(Object obj, NbtCompound nbt) {
        return serialize((T) obj, nbt);
    }

    NbtCompound serialize(T obj, NbtCompound nbt);
}
