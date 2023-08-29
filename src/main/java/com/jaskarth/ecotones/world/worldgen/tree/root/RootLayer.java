package com.jaskarth.ecotones.world.worldgen.tree.root;

import com.jaskarth.ecotones.util.layer.Layer;

import java.util.Random;

public class RootLayer extends Layer {
    public RootLayer(long seed) {
        super(seed);
    }

    @Override
    protected int[][] operate(int[][] data, Random random, int x, int z, int width, int height) {
        checkSize(data, 3, 3);

        for (int x1 = 0; x1 < data.length; x1++) {
            for (int z1 = 0; z1 < data[x1].length; z1++) {
                // Make in a + shape
                if (x1 == 1 || z1 == 1) {
                    data[x1][z1] = random.nextInt(3);
                }
            }
        }

        return data;
    }
}
