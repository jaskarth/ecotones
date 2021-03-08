package supercoder79.ecotones.grass;

import net.minecraft.block.Blocks;
import supercoder79.ecotones.blocks.EcotonesBlocks;

public class GrassSelectors {
    public static final GrassSelector HOT_GRASS = new WeightedGrassSelector()
            .add(Blocks.GRASS.getDefaultState(), 3, 4)
            .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 8, 2);

    public static final GrassSelector TALL_GRASS = new NoiseBasedGrassSelector(Blocks.TALL_GRASS.getDefaultState(), 0.5, 4);
}
