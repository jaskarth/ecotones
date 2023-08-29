package com.jaskarth.ecotones.world.entity.ai.system;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public final class AiState {
    private final List<Feeling> feelings = new ArrayList<>();

    private final Stomach stomach;

    public AiState(Stomach stomach) {
        this.stomach = stomach;
    }

    public NbtCompound serialize(NbtCompound nbt) {
        nbt.put("Stomach", this.stomach.serialize(new NbtCompound()));

        NbtList feelings = new NbtList();

        for (Feeling feeling : this.feelings) {
            feelings.add(feeling.serialize(new NbtCompound()));
        }

        nbt.put("Feelings", feelings);

        return nbt;
    }

    public void deserialize(NbtCompound nbt) {
        this.stomach.deserialize(nbt.getCompound("Stomach"));

        for (NbtElement el : nbt.getList("Feelings", NbtElement.COMPOUND_TYPE)) {
            this.feelings.add(Feeling.deserialize((NbtCompound) el));
        }
    }

    public Stomach getStomach() {
        return stomach;
    }

    public List<Feeling> getFeelings() {
        return feelings;
    }
}
