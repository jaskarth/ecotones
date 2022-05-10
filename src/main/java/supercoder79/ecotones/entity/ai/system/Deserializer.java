package supercoder79.ecotones.entity.ai.system;

import net.minecraft.nbt.NbtCompound;

@FunctionalInterface
public interface Deserializer<T> {
    T deserialize(NbtCompound nbt);
}