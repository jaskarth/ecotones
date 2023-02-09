package supercoder79.ecotones.world.gen.caves;

import supercoder79.ecotones.world.gen.NoiseColumn;

public interface NoiseCaveGenerator {
    void init(long seed);

    void genColumn(int x, int z, NoiseColumn values);
}
