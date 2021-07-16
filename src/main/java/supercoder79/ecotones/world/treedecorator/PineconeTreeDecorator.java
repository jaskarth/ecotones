package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class PineconeTreeDecorator extends TreeDecorator {
    public static final Codec<PineconeTreeDecorator> CODEC = Codec.INT.fieldOf("rarity").xmap(PineconeTreeDecorator::new, decorator -> decorator.rarity).stable().codec();
    private final int rarity;

    public PineconeTreeDecorator(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        for (BlockPos pos : leavesPositions) {
            if (world.testBlockState(pos.down(), s -> s.isAir())) {
                if (random.nextInt(rarity) == 0) {
                    BlockPos downPos = pos.down();
                    if (world.testBlockState(downPos, s -> s.isAir())) {
                        replacer.accept(downPos, EcotonesBlocks.PINECONE.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return EcotonesTreeDecorators.PINECONES;
    }
}
