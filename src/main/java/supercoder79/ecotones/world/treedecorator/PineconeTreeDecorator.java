package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.decorator.TreeDecorator;
import net.minecraft.world.gen.decorator.TreeDecoratorType;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class PineconeTreeDecorator extends TreeDecorator {
    public static final Codec<PineconeTreeDecorator> CODEC = Codec.INT.fieldOf("rarity").xmap(PineconeTreeDecorator::new, decorator -> decorator.rarity).stable().codec();
    private final int rarity;

    public PineconeTreeDecorator(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public void generate(WorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> set, BlockBox box) {
        for (BlockPos pos : leavesPositions) {
            if (world.getBlockState(pos.down()).isAir()) {
                if (random.nextInt(rarity) == 0) {
                    world.setBlockState(pos.down(), EcotonesBlocks.PINECONE.getDefaultState(), 3);
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return EcotonesTreeDecorators.PINECONES;
    }
}
