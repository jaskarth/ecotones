package com.jaskarth.ecotones.world.worldgen.tree.trait.smallspruce;

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
