package supercoder79.ecotones.util.layer;

public abstract class Layer {
    private final long seed;

    public Layer(long seed) {
        this.seed = seed;
    }

    public abstract void operate(int[][] data, int x, int z, int width, int height);

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

    protected void ensureArray(int[][] data) {
        int size = data[0].length;

        for (int i = 1; i < data.length; i++) {
            if (data[i].length != size) {
                throw new RuntimeException("The array has improper dimensions! How did this happen?!");
            }
        }
    }
}
