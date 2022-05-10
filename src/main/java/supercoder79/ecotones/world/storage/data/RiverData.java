package supercoder79.ecotones.world.storage.data;

import net.minecraft.nbt.NbtCompound;

public record RiverData(boolean openToAir) {
    // OA -> OpenToAir

    public static NbtCompound serialize(RiverData data, NbtCompound nbt) {
        nbt.putBoolean("OA", data.openToAir);

        return nbt;
    }

    public static RiverData deserialize(NbtCompound nbt) {
        return new RiverData(nbt.getBoolean("OA"));
    }
}
