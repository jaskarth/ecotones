package supercoder79.ecotones.world.tree.trait;

import java.util.Random;

public interface SmallSpruceTrait extends Trait {
    default int extraHeight(Random random) {
        return random.nextInt(4);
    }

    default double maxRadius(Random random) {
        return 1.8 + ((random.nextDouble() - 0.5) * 0.2);
    }

    // -0.15x^{2}-x+1.3
    default double model(double x) {
        return -0.15 * (x * x) - x + 1.3;
    }
}
