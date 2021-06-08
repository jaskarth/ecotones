package supercoder79.ecotones.blocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

public class SimpleSaplingBlock extends SaplingBlock {
    public SimpleSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }

    public SimpleSaplingBlock(SaplingGenerator generator) {
        this(generator, Settings.copy(Blocks.OAK_SAPLING));
    }
}