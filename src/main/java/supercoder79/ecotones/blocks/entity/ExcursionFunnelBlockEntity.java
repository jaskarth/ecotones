package supercoder79.ecotones.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import supercoder79.ecotones.client.particle.EcotonesParticles;

import java.util.List;

public class ExcursionFunnelBlockEntity extends BlockEntity {
    public ExcursionFunnelBlockEntity(BlockPos pos, BlockState state) {
            super(EcotonesBlockEntities.EXCURSION_FUNNEL, pos, state);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, ExcursionFunnelBlockEntity blockEntity) {
        ClientWorld clientWorld = (ClientWorld) world;

        if (world.getTime() % 4 == 0) {
            Random random = world.random;

            clientWorld.addParticle(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, random.nextDouble() * 0.005, 0.4, random.nextDouble() * 0.005);

            if (world.getTime() % 8 == 0) {
                clientWorld.addParticle(EcotonesParticles.ASBESTOS, pos.getX() + 0.5, pos.getY() + 0.825, pos.getZ() + 0.5, random.nextDouble() * 0.005, 0.4, random.nextDouble() * 0.005);
            }
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, ExcursionFunnelBlockEntity blockEntity) {
        if (world.getTime() % 2 == 0) {
            // Rectangular box upwards
            List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, new Box(pos.getX() - 0.5, pos.getY() - 0.5, pos.getZ() - 0.5, pos.getX() + 0.5, pos.getY() + 5, pos.getZ() + 0.5));

            for (LivingEntity entity : list) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 0, true, false));
            }

            // TODO: slow down players moving through the box, so it feels like they get captured
        }
    }
}
