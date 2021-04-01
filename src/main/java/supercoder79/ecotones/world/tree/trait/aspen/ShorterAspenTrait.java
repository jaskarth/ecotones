package supercoder79.ecotones.world.tree.trait.aspen;

import java.util.Random;

public class ShorterAspenTrait implements AspenTrait {
    @Override
    public int leafDistance(Random random) {
        return random.nextInt(2) + 1;
    }

    @Override
    public String name() {
        return "Shorter";
    }
}
