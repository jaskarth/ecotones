package supercoder79.ecotones.tree.poplar;

import supercoder79.ecotones.tree.PoplarTrait;

import java.util.Random;

public class ShortPoplarTrait implements PoplarTrait {
    @Override
    public int extraHeight(Random random) {
        return 2;
    }

    @Override
    public String name() {
        return "Short";
    }
}
