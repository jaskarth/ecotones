package supercoder79.ecotones.util.layer;

import supercoder79.ecotones.util.ImprovedChunkRandom;

import java.util.Random;

public abstract class Layer {
    protected final long seed;

    public Layer(long seed) {
        this.seed = seed;
    }

    protected abstract int[][] operate(int[][] data, Random random, int x, int z, int width, int height);

    public int[][] operate(int x, int z, int width, int height) {
        int[][] data = new int[width][height];

        ImprovedChunkRandom random = new ImprovedChunkRandom(0);
        random.setPopulationSeed(this.seed, x, z);

        return operate(data, random, x, z, width, height);
    }

    protected int[][] emptyCopy(int[][] data) {
        ensureArray(data);
        return new int[data.length][data[0].length];
    }

    protected int[][] copy(int[][] data) {
        int[][] copy = emptyCopy(data);

        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, copy[i], 0, data[i].length);
        }

        return copy;
    }

    protected void checkSize(int[][] data, int width, int height) {
        if (data.length != width || data[0].length != height) {
            throw new RuntimeException(this.getClass().getSimpleName() + " requires a dataset size of [" + width + "][" + height + "]");
        }
    }

    protected void ensureArray(int[][] data) {
        int size = data[0].length;

        for (int i = 1; i < data.length; i++) {
            if (data[i].length != size) {
                throw new RuntimeException("The array has improper dimensions! How did this happen?!");
            }
        }
    }
}
