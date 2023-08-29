package com.jaskarth.ecotones.world.worldgen.tree.trait.poplar;

import java.util.Random;

public class ShortPoplarTrait implements PoplarTrait {
    @Override
    public int extraHeight(Random random) {
        return 2;
    }

    @Override
    public String name() {
        return "Short";
    }
}
