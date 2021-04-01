package supercoder79.ecotones.world.tree.trait.aspen;

import supercoder79.ecotones.world.tree.trait.Trait;

import java.util.Random;

public interface AspenTrait extends Trait {
    default double maxRadius(Random random) {
        return 2 + ((random.nextDouble() - 0.5) * 0.2);
    }

    default int leafDistance(Random random) {
        return random.nextInt(4) + 3;
    }

    default double model(double x) {
        return -Math.pow(((1.4 * x) - 0.3), 2) + 1.2;
    }

    default double branchThreshold(Random random) {
        return 2.1;
    }
}
