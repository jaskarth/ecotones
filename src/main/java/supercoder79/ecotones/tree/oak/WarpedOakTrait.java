package supercoder79.ecotones.tree.oak;

import supercoder79.ecotones.tree.OakTrait;

import java.util.Random;

public class WarpedOakTrait implements OakTrait {

    @Override
    public double getPitch(Random random) {
        return (random.nextDouble() - 0.5) * 0.275;
    }

    @Override
    public double branchChance() {
        return 0.875;
    }

    @Override
    public String name() {
        return "Warped";
    }
}
