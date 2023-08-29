package com.jaskarth.ecotones.world.entity.ai.system;

import net.minecraft.nbt.NbtCompound;

@FunctionalInterface
public interface Deserializer<T> {
    T deserialize(NbtCompound nbt);
}