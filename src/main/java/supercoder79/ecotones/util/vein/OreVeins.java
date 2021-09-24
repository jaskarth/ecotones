package supercoder79.ecotones.util.vein;

import net.minecraft.block.Blocks;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.ArrayList;
import java.util.List;

public final class OreVeins {
    public static final OreVein IRON = new OreVein(2, 42, 8,
            EcotonesBlocks.PYRITE.getDefaultState(), Blocks.IRON_ORE.getDefaultState(), Blocks.RAW_IRON_BLOCK.getDefaultState(),
            new OreVein.Point(0.2, -0.05));

    public static final OreVein COPPER = new OreVein(8, 54, 12,
            EcotonesBlocks.MALACHITE.getDefaultState(), Blocks.COPPER_ORE.getDefaultState(), Blocks.RAW_COPPER_BLOCK.getDefaultState(),
            new OreVein.Point(-0.2, 0.1));

    public static final OreVein GOLD = new OreVein(2, 28, 4,
            EcotonesBlocks.SPARSE_GOLD_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState(), Blocks.RAW_GOLD_BLOCK.getDefaultState(),
            new OreVein.Point(0.6, 0.4));

    public static final List<OreVein> VEINS = new ArrayList<>();

    public static void init() {
        VEINS.add(IRON);
        VEINS.add(COPPER);
        VEINS.add(GOLD);
    }
}
