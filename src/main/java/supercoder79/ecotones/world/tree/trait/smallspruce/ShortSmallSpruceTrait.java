package supercoder79.ecotones.world.tree.trait.smallspruce;

import supercoder79.ecotones.world.tree.trait.SmallSpruceTrait;

import java.util.Random;

public class ShortSmallSpruceTrait implements SmallSpruceTrait {
    @Override
    public int extraHeight(Random random) {
        return 0;
    }

    @Override
    public String name() {
        return "Short";
    }
}
