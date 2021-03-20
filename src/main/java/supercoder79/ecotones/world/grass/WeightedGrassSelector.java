package supercoder79.ecotones.world.grass;

import net.minecraft.block.BlockState;
import net.minecraft.util.collection.WeightedList;
import supercoder79.ecotones.world.data.DataHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeightedGrassSelector extends GrassSelector {
    private final Map<BlockState, Integer> weights = new HashMap<>();
    private final WeightedList<BlockState> list = new WeightedList<>();

    public WeightedGrassSelector add(BlockState state, int weight, int grassWeight) {
        this.list.add(state, weight);
        this.weights.put(state, grassWeight);

        return this;
    }

    @Override
    public Selection select(int x, int z, Random random, DataHolder data) {
        BlockState state = this.list.pickRandom(random);
        return new Selection(state, this.weights.get(state));
    }
}
