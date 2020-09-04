package supercoder79.ecotones.tree.poplar;

import supercoder79.ecotones.tree.PoplarTrait;

import java.util.Random;

public class WidePoplarTrait implements PoplarTrait {
    @Override
    public double maxRadius(Random random) {
        return 2.8 + (random.nextDouble() * 0.2);
    }

    @Override
    public String name() {
        return "Wide";
    }
}
