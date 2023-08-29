package com.jaskarth.ecotones.world.entity.ai.system;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

// A serializable goal, basically
public abstract class Action {
    private State state = State.PARKED;

    // Run every tick that the action is parked
    public abstract boolean shouldStart(AiState state);

    // Called when starting to run an action, or when a running action is deserialized
    public void start(AiState state) {

    }

    // Runs every tick while the action is running
    public abstract boolean shouldContinue(AiState state);

    public void stop(AiState state) {

    }

    // Runs every tick
    public void tick(AiState state) {

    }

    public abstract Identifier key();

    // Which actions should take precedence
    public abstract int priority();

    public boolean isInterruptable() {
        return true;
    }

    public abstract NbtCompound serialize(NbtCompound nbt);

    public final NbtCompound serializeState(NbtCompound nbt) {
        nbt.putInt("State", this.state.ordinal());

        return serialize(nbt);
    }

    public abstract void deserialize(NbtCompound nbt);

    public final void deserializeState(NbtCompound nbt) {
        this.state = State.values()[nbt.getInt("State")];

        deserialize(nbt);
    }

    public final State state() {
        return this.state;
    }

    public final void setState(State state) {
        this.state = state;
    }

    public enum State {
        RUNNING,
        PARKED
    }

    public abstract Control control();

    public enum Control {
        MOVE,
        LOOK
    }
}
