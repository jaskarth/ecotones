package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThornBushBlock extends EcotonesGrassBlock{
    protected ThornBushBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.slowMovement(state, new Vec3d(0.9, 0.85, 0.9));
        }
    }
}
