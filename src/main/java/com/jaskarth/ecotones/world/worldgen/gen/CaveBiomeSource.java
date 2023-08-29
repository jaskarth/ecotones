package com.jaskarth.ecotones.world.worldgen.gen;

import com.jaskarth.ecotones.api.CaveBiome;

public interface CaveBiomeSource {
    CaveBiome getCaveBiomeForNoiseGen(int x, int z);
}
