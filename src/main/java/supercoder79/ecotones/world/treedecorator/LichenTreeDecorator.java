package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.decorator.TreeDecorator;
import net.minecraft.world.gen.decorator.TreeDecoratorType;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class LichenTreeDecorator extends TreeDecorator {
    public static final Codec<LichenTreeDecorator> CODEC = Codec.INT.fieldOf("chance").xmap(LichenTreeDecorator::new, decorator -> decorator.chance).stable().codec();

    private final int chance;

    public LichenTreeDecorator(int chance) {
        this.chance = chance;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return EcotonesTreeDecorators.LICHEN;
    }

    @Override
    public void generate(WorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> set, BlockBox box) {
        for (BlockPos pos : logPositions) {
            if (random.nextInt(this.chance) == 0) {
                // Get random direction
                Direction dir = randomDirection(random);
                if (world.getBlockState(pos.offset(dir)).isAir()) {
                    // Place lichen towards trunk- so get opposite and then place
                    world.setBlockState(pos.offset(dir), EcotonesBlocks.LICHEN.getDefaultState().with(VineBlock.FACING_PROPERTIES.get(dir.getOpposite()), true), 3);
                }
            }
        }
    }

    private Direction randomDirection(Random random) {
        return Direction.fromHorizontal(random.nextInt(4));
    }
}
