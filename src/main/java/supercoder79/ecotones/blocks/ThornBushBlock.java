package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ThornBushBlock extends EcotonesPlantBlock {
    protected ThornBushBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.slowMovement(state, new Vec3d(0.9, 0.85, 0.9));
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        boolean superPlaced = super.canPlaceAt(state, world, pos);
        if (superPlaced) {
            return true;
        }

        BlockState downState = world.getBlockState(pos.down());
        return (downState.isOf(Blocks.SAND) || downState.isOf(Blocks.RED_SAND) || downState.isOf(EcotonesBlocks.RED_ROCK));
    }
}
