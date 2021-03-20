package supercoder79.ecotones.world.tree.trait.oak;

import supercoder79.ecotones.world.tree.trait.OakTrait;

public class ThinOakTrait implements OakTrait {
    @Override
    public boolean generateThickTrunk() {
        return false;
    }

    @Override
    public int scaleHeight(int originalHeight) {
        return (int) (originalHeight * 0.94);
    }

    @Override
    public double branchChance() {
        return 0.95;
    }

    @Override
    public String name() {
        return "Thin";
    }
}
