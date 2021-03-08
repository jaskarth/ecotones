package supercoder79.ecotones.grass;

import net.minecraft.block.BlockState;
import supercoder79.ecotones.data.DataHolder;

import java.util.Random;

public abstract class GrassSelector {
    public abstract Selection select(int x, int z, Random random, DataHolder data);

    public static class Selection {
        public final BlockState state;
        public final int weight;

        public Selection(BlockState state, int weight) {
            this.state = state;
            this.weight = weight;
        }
    }
}
