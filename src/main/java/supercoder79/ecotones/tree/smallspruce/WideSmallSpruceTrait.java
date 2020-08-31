package supercoder79.ecotones.tree.smallspruce;

import supercoder79.ecotones.tree.SmallSpruceTrait;

import java.util.Random;

public class WideSmallSpruceTrait implements SmallSpruceTrait {
    @Override
    public double maxRadius(Random random) {
        return 2.05 + ((random.nextDouble() - 0.5) * 0.1);
    }

    @Override
    public double model(double x) {
        return -0.15 * (x * x) - (1.1 * x) + 1.4;
    }

    @Override
    public String name() {
        return "Wide";
    }
}
