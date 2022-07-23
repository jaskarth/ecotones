package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import supercoder79.ecotones.client.particle.EcotonesParticles;

public class MapleLeavesBlock extends LeavesBlock {
    public MapleLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (random.nextInt(100) == 0) {
            if (world.getBlockState(pos.down()).isAir()) {
                world.addParticle(EcotonesParticles.MAPLE_LEAF, pos.getX() + random.nextDouble(), pos.getY() - 0.1, pos.getZ() + random.nextDouble(), 0, 0, 0);
            }
        }
    }
}
