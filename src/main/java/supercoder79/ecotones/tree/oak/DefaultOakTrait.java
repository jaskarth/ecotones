package supercoder79.ecotones.tree.oak;

import supercoder79.ecotones.tree.OakTrait;

import java.util.Random;

public class DefaultOakTrait implements OakTrait {
    public static final DefaultOakTrait INSTANCE = new DefaultOakTrait();

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
        return 0;
    }

    @Override
    public double branchChance() {
        return 1;
    }

    @Override
    public String name() {
        return "Default";
    }
}
