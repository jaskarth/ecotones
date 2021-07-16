package supercoder79.ecotones.world.tree.root;

import supercoder79.ecotones.util.layer.Layer;
import supercoder79.ecotones.util.layer.ParentedLayer;

import java.util.Random;

public class RootSmoothLayer extends ParentedLayer {
    public RootSmoothLayer(long seed, Layer parent) {
        super(seed, parent);
    }

    @Override
    protected int[][] operate(int[][] data, Random random, int x, int z, int width, int height) {
        int[][] smoothed = copy(data);

        for (int x1 = 0; x1 < data.length; x1++) {
            for (int z1 = 0; z1 < data[x1].length; z1++) {
                // Only sample for places in X
                if ((x1 == 0 || x1 == 2) && (z1 == 0 || z1 == 2)) {
                    // Find the + positions from the X
                    int xOff = (x1 + 1) % 2;
                    int zOff = (z1 + 1) % 2;

                    int n1 = data[xOff][z1];
                    int n2 = data[x1][zOff];

                    // If both neighbors are 2, the corner can exist
                    if (n1 == 2 && n2 == 2) {
                        smoothed[x1][z1] = random.nextInt(2);
                    }

                    // If 1 is 2 and the other is 0, smooth it out

                    if (n1 == 2 && n2 == 0) {
                        smoothed[xOff][z1] -= random.nextInt(2);
                    }

                    if (n1 == 0 && n2 == 2) {
                        smoothed[x1][zOff] -= random.nextInt(2);
                    }
                }
            }
        }

        return smoothed;
    }
}
