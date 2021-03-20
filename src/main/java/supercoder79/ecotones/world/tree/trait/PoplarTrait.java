package supercoder79.ecotones.world.tree.trait;

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

    // Don't ask me why it's like this. https://twitter.com/SuperCoder79/status/1301333545630261254
    default double leafRadius(int leafHeight, Random random) {
        return leafHeight - 1;
    }
}
