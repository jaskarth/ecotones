package com.jaskarth.ecotones.world.worldgen.tree.trait.poplar;

import com.jaskarth.ecotones.world.worldgen.tree.trait.Trait;

import java.util.Random;

public interface PoplarTrait extends Trait {
    default int extraHeight(Random random) {
        return random.nextInt(3) + 2;
    }

    default double maxRadius(Random random) {
        return 2.6 + ((random.nextDouble() - 0.5) * 0.2);
    }

    default double model(double x) {
        return (-2 * (x * x * x)) + (1.9 * x) + 0.2;
    }

    default int leafHeight(Random random) {
        return 12;
    }

    // I made a stupid mistake with this code but it looks better this way so it stays
    default double leafRadius(int leafHeight, Random random) {
        return leafHeight - 1;
    }
}
