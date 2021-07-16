package supercoder79.ecotones.world.tree.trait.poplar;

import java.util.Random;

public class TaperedPoplarTrait implements PoplarTrait {
    @Override
    public int leafHeight(Random random) {
        return 13;
    }

    @Override
    public int extraHeight(Random random) {
        return random.nextInt(2) + 3;
    }

    @Override
    public double model(double x) {
        return (-3.1 * (x * x * x)) + (2.3 * x) + 0.2;
    }

    @Override
    public String name() {
        return "Tapered";
    }
}
