package com.jaskarth.ecotones.test;

import com.jaskarth.ecotones.util.Vec2i;
import com.jaskarth.ecotones.util.noise.voronoi.Voronoi;
import com.jaskarth.ecotones.util.noise.voronoi.VoronoiRaster;

import java.util.Map;
import java.util.Random;

public class TestVoronoi {
//    public static void main(String[] args) {
//        Voronoi vn = new Voronoi(100);
//        Random random = new Random();
//        ImageDumper.dumpImage("voronoi.png", 512, (x, z) -> {
//            int i = vn.get(x / 20.0, z / 20.0);
//            random.setSeed(i);
//
//            int r = random.nextInt(256);
//            int g = random.nextInt(256);
//            int b = random.nextInt(256);
//
//            return ImageDumper.getIntFromColor(r, g, b);
//        });
//    }

    // Raster test
    public static void main(String[] args) {
        VoronoiRaster vn = new VoronoiRaster(100, 0, 0, 64, 24);

        Random random = new Random();
        Map<Vec2i, Integer> postToCol = vn.getPosToColor();
        ImageDumper.dumpImage("voronoi.png", 512, (x, z) -> {
            Integer i = postToCol.get(Vec2i.of(x, z));
            if (i != null) {
                if (true) {
                    return ImageDumper.getIntFromColor((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
                }
                random.setSeed(i);

                int r = 120 + random.nextInt(40) - random.nextInt(40);
                int g = 200 + random.nextInt(22) - random.nextInt(22);
                int b = 40 + random.nextInt(30) - random.nextInt(30);

                return ImageDumper.getIntFromColor(r, g, b);
            }

            // Not found
            return 0;
        });
    }
}
