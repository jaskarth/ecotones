package supercoder79.ecotones.world.tree.trait.aspen;

import java.util.Random;

public class ThinAspenTrait implements AspenTrait {
    @Override
    public double maxRadius(Random random) {
        return 1.7 + random.nextDouble() * 0.2;
    }

    @Override
    public String name() {
        return "Thin";
    }
}
