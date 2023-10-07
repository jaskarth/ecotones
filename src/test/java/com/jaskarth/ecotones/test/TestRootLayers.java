package com.jaskarth.ecotones.test;

import com.jaskarth.ecotones.util.layer.Layer;
import com.jaskarth.ecotones.world.worldgen.tree.root.RootLayers;

import java.util.Random;

public class TestRootLayers {
    public static void main(String[] args) {
        Layer layer = RootLayers.create(new Random().nextLong());

        int[][] data = layer.operate(0, 0, 3, 3);

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.println("X: " + i + " Z: " + j + " -> " + data[i][j]);
            }
        }
    }
}
