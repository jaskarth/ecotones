package com.jaskarth.ecotones.test;

import com.jaskarth.ecotones.world.worldgen.gen.caves.EcotonesCaveGenerator;

public class TestCaveGen {
    public static void main(String[] args) {
        EcotonesCaveGenerator gen = new EcotonesCaveGenerator();
        gen.init(200);

        for (int j = 0; j < 10; j++) {
            double[] values = new double[32 + 8 + 8];
            values[8] = -1;


            System.out.println("========================");
            gen.genColumn(j * 10, 0, null);

            for (int i = 0; i < values.length; i++) {
                System.out.println((i - 8) + " " + values[i]);
            }
        }
    }
}
