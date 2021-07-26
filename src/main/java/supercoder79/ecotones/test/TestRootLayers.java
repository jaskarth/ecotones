package supercoder79.ecotones.test;

import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.util.layer.Layer;
import supercoder79.ecotones.world.tree.root.RootLayers;

import java.util.Random;

@DevOnly
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
