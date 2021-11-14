package supercoder79.ecotones.entity.ai.system;

import com.google.common.collect.Comparators;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.*;
import java.util.stream.IntStream;

// Blended mix of goal and brain systems
public final class Ai {
    private final List<? extends Memory> memories;
    private final List<? extends Action> actions;
    private final List<Integer> indices = new ArrayList<>();
    private final AiState state;
    private final Set<Action.Control> usedControls = new HashSet<>();
    private int lowestPriority = Integer.MAX_VALUE;

    public Ai(List<? extends Memory> memories, List<? extends Action> actions, AiState state) {
        this.memories = memories;
        this.actions = actions;
        this.state = state;

        List<? extends Action> sortedActions = new ArrayList<>(actions);
        sortedActions.sort(Comparator.comparingInt(Action::priority).reversed());

        for (int i = 0; i < sortedActions.size(); i++) {
            indices.add(actions.indexOf(sortedActions.get(i)));
        }
    }

    public void tick() {
        int lowestPriority = Integer.MAX_VALUE;
        for (int i : this.indices) {
            Action a = this.actions.get(i);

            if (a.state() == Action.State.RUNNING) {
                a.tick(this.state);

                if (!a.shouldContinue(this.state)) {
                    a.setState(Action.State.PARKED);
                    a.stop(this.state);
                    usedControls.remove(a.control());
                }

                lowestPriority = Math.min(lowestPriority, a.priority());
            } else {
                // TODO: priority calculation is scuffed
                // TODO: interrupting is not a thing but should be
                if (a.shouldStart(this.state) && !this.usedControls.contains(a.control()) && a.priority() <= this.lowestPriority) {
                    a.setState(Action.State.RUNNING);
                    a.start(this.state);
                    this.usedControls.add(a.control());

                    lowestPriority = Math.min(lowestPriority, a.priority());
                }
            }
        }

        if (this.lowestPriority != lowestPriority) {
            this.lowestPriority = lowestPriority;
        }
    }

    public NbtCompound serialize(NbtCompound nbt) {
        nbt.put("State", this.state.serialize(new NbtCompound()));

        NbtList actions = new NbtList();
        for (Action action : this.actions) {
            NbtCompound actionCompound = new NbtCompound();
            actionCompound.putString("Type", action.key().toString());

            actions.add(action.serializeState(actionCompound));
        }

        nbt.put("Actions", actions);

        nbt.putIntArray("Controls", this.usedControls.stream().mapToInt(Action.Control::ordinal).toArray());

        nbt.putInt("LowestPriority", this.lowestPriority);

        return nbt;
    }

    public void deserialize(NbtCompound nbt) {
        this.state.deserialize(nbt.getCompound("State"));

        IntStream.of(nbt.getIntArray("Controls")).forEach(i -> this.usedControls.add(Action.Control.values()[i]));

        this.lowestPriority = nbt.getInt("LowestPriority");

        NbtList actions = nbt.getList("Actions", NbtElement.COMPOUND_TYPE);
        boolean isSame = actions.size() == this.actions.size();

        if (isSame) {
            for (int i = 0; i < actions.size(); i++) {
                NbtCompound actionCompound = actions.getCompound(i);
                Action action = this.actions.get(i);

                if (!action.key().toString().equals(actionCompound.getString("Type"))) {
                    isSame = false;
                }
            }
        }

        // Only deserialize if same- otherwise lmao entity borked
        if (isSame) {
            for (int i = 0; i < actions.size(); i++) {
                NbtCompound actionCompound = actions.getCompound(i);
                Action action = this.actions.get(i);
                action.deserializeState(actionCompound);
            }
        }
    }

    public AiState getState() {
        return state;
    }
}
