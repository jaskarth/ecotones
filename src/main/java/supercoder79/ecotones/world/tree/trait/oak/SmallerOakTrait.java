package supercoder79.ecotones.world.tree.trait.oak;

import java.util.Random;

// TODO: rename this
public class SmallerOakTrait implements OakTrait {
    @Override
    public boolean generateThickTrunk() {
        return false;
    }

    @Override
    public double getPitch(Random random) {
        return (random.nextDouble() - 0.5) * 0.025;
    }

    @Override
    public double branchChance() {
        return 0.695;
    }

    @Override
    public String name() {
        return "Smaller";
    }
}
