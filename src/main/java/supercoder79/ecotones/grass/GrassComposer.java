package supercoder79.ecotones.grass;

import net.minecraft.block.BlockState;
import net.minecraft.util.collection.WeightedList;
import supercoder79.ecotones.data.DataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class GrassComposer {
    public final List<GrassSelector> selectors = new ArrayList<>();

    public GrassComposer() {

    }

    public GrassComposer add(GrassSelector selector) {
        this.selectors.add(selector);

        return this;
    }

    public BlockState select(int x, int z, Random random, DataHolder data) {
        WeightedList<BlockState> states = new WeightedList<>();

        for (GrassSelector selector : this.selectors) {
            GrassSelector.Selection selection = selector.select(x, z, random, data);
            states.add(selection.state, selection.weight);
        }

        return states.pickRandom(random);
    }
}
