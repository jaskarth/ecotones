package supercoder79.ecotones.tree;

import java.util.Random;

//TODO: use default methods
public interface OakTrait extends Trait {
    boolean generateThickTrunk();

    int scaleHeight(int originalHeight);

    double getPitch(Random random);

    double branchChance();

    String name();
}
