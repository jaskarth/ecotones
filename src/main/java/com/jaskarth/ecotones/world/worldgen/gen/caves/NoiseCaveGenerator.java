package com.jaskarth.ecotones.world.worldgen.gen.caves;

import com.jaskarth.ecotones.world.worldgen.gen.NoiseColumn;

public interface NoiseCaveGenerator {
    void init(long seed);

    void genColumn(int x, int z, NoiseColumn values);
}
