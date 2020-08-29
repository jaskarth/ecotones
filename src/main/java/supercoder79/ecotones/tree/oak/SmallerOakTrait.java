package supercoder79.ecotones.tree.oak;

import supercoder79.ecotones.tree.OakTrait;

import java.util.Random;

// TODO: rename this
public class SmallerOakTrait implements OakTrait {
    @Override
    public boolean generateThickTrunk() {
        return true;
    }

    @Override
    public int scaleHeight(int originalHeight) {
        return originalHeight;
    }

    @Override
    public double getPitch(Random random) {
        return (random.nextDouble() - 0.5) * 0.025;
    }

    @Override
    public double branchChance() {
        return 0.65;
    }

    @Override
    public String name() {
        return "Smaller";
    }
}
