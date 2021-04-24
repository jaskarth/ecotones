package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PoofyDandelionBlock extends EcotonesGrassBlock{
    protected PoofyDandelionBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
            double dX = Math.abs(entity.getX() - entity.lastRenderX);
            double dZ = Math.abs(entity.getZ() - entity.lastRenderZ);
            Random random = world.getRandom();
            if ((dX >= 0.1 || dZ >= 0.1) && random.nextInt(8) == 0) {
                // poof!
                ((ServerWorld)world).spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, random.nextInt(3), random.nextGaussian() * 0.015, random.nextGaussian() * 0.005, random.nextGaussian() * 0.015, 0.025f);
            }
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);

        if (!world.isClient()) {
            Random random = world.getRandom();
            ((ServerWorld)world).spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, random.nextInt(3) + 1, random.nextGaussian() * 0.015, random.nextGaussian() * 0.005, random.nextGaussian() * 0.015, 0.025f);
        }
    }
}
