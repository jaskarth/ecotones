package supercoder79.ecotones.world.gen;

import supercoder79.ecotones.api.CaveBiome;

public interface CaveBiomeSource {
    CaveBiome getCaveBiomeForNoiseGen(int x, int z);
}
