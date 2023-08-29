package com.jaskarth.ecotones.world.worldgen.tree.trait.smallspruce;

import java.util.Random;

public class ThinSmallSpruceTrait implements SmallSpruceTrait {
    @Override
    public double maxRadius(Random random) {
        return 1.6 + (random.nextDouble() * 0.3);
    }

    @Override
    public String name() {
        return "Thin";
    }
}
