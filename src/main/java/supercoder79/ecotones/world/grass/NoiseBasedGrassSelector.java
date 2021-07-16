package supercoder79.ecotones.world.grass;

import net.minecraft.block.BlockState;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.EcotonesData;

import java.util.Random;

public class NoiseBasedGrassSelector extends GrassSelector {
    private final BlockState state;
    private final double noiseLevel;
    private final int weight;

    public NoiseBasedGrassSelector(BlockState state, double noiseLevel, int weight) {
        this.state = state;
        this.noiseLevel = noiseLevel;
        this.weight = weight;
    }

    @Override
    public Selection select(int x, int z, Random random, DataHolder data) {
        // TODO: not sure if returning 0 is valid but it seems to work
        int weight = data.get(EcotonesData.GRASS_NOISE).get(x, z) > this.noiseLevel ? this.weight : 0;
        return new Selection(this.state, weight);
    }
}
