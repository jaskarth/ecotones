package supercoder79.ecotones.util.layer;

import supercoder79.ecotones.util.ImprovedChunkRandom;

import java.util.Random;

public abstract class ParentedLayer extends Layer {
    private final Layer parent;

    public ParentedLayer(long seed, Layer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[][] operate(int x, int z, int width, int height) {
        int[][] data = this.parent.operate(x, z, width, height);

        ImprovedChunkRandom random = new ImprovedChunkRandom();
        random.setPopulationSeed(this.seed, x, z);

        return operate(data, random, x, z, width, height);
    }
}
