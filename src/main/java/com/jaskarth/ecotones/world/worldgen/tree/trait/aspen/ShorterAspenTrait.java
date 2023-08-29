package com.jaskarth.ecotones.world.worldgen.tree.trait.aspen;

import java.util.Random;

public class ShorterAspenTrait implements AspenTrait {
    @Override
    public int leafDistance(Random random) {
        return random.nextInt(2) + 2;
    }

    @Override
    public String name() {
        return "Shorter";
    }
}
