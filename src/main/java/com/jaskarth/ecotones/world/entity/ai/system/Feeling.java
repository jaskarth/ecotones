package com.jaskarth.ecotones.world.entity.ai.system;

import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

// Emotion towards a player. Positive or negative.
// Use case: store how much an entity trusts a player, and whether it should run away
public final class Feeling {
    private final UUID player;
    private double feeling = 0;

    public Feeling(UUID player) {
        this.player = player;
    }

    public Feeling(UUID player, double feeling) {
        this.player = player;
        this.feeling = feeling;
    }

    public boolean matchesPlayer(UUID player) {
        return this.player.equals(player);
    }

    public double getFeeling() {
        return this.feeling;
    }

    public void addFeeling(double feeling) {
        this.feeling += feeling;
    }

    public NbtCompound serialize(NbtCompound nbt) {
        nbt.putUuid("Player", this.player);
        nbt.putDouble("Feeling", this.feeling);

        return nbt;
    }

    public static Feeling deserialize(NbtCompound nbt) {
        return new Feeling(nbt.getUuid("Player"), nbt.getDouble("Feeling"));
    }
}
