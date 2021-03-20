package supercoder79.ecotones.world.tree.trait.poplar;

import supercoder79.ecotones.world.tree.trait.PoplarTrait;

import java.util.Random;

public class WidePoplarTrait implements PoplarTrait {
    @Override
    public double maxRadius(Random random) {
        return 2.7 + (random.nextDouble() * 0.1);
    }

    @Override
    public String name() {
        return "Wide";
    }
}
