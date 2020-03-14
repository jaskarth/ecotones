package supercoder79.ecotones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.Random;

public class GeyserBlock extends Block {
    public GeyserBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos.up()).isOpaque()) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 6f, Explosion.DestructionType.DESTROY);
            world.spawnParticles(ParticleTypes.POOF, pos.getX(), pos.getY()+0.125, pos.getZ(), 200, random.nextDouble()*0.005, 0.65, random.nextDouble()*0.005, 0.75f);
        }

        world.spawnParticles(ParticleTypes.POOF, pos.getX(), pos.getY()+0.125, pos.getZ(), 50, random.nextDouble()*0.005, 1, random.nextDouble()*0.005, 0.75f);
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, new Box(pos.getX()-2, pos.getY()-2, pos.getZ()-2, pos.getX()+2, pos.getY()+2, pos.getZ()+2));
        for (LivingEntity entity : list) {
            entity.addVelocity(0, 1.25, 0);
            entity.damage(DamageSource.GENERIC, 4);
        }
    }
}
