package com.jaskarth.ecotones.client.render.magnifying;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.blocks.entity.FertilizerSpreaderBlockEntity;

public class FertilizerSpreaderRenderer extends MagnifyingGlassRenderer {
    @Override
    public void render(BlockPos pos, ClientPlayerEntity player) {
        BlockState state = player.clientWorld.getBlockState(pos);

        if (!state.isOf(EcotonesBlocks.FERTILIZER_SPREADER)) {
            return;
        }

        // Should be safe!
        FertilizerSpreaderBlockEntity spreader = (FertilizerSpreaderBlockEntity) player.clientWorld.getBlockEntity(pos);

        // Be extra careful
        if (spreader == null) {
            return;
        }

        if (player.clientWorld.getTime() % 10 == 0) {
            createParticles(player.clientWorld, pos, 10);

            for (BlockPos blockPos : spreader.getWater()) {
                createParticles(player.clientWorld, blockPos, 1);
            }

            for (BlockPos blockPos : spreader.getFarmland()) {
                createParticles(player.clientWorld, blockPos, 3);
            }
        }
    }

    public static void createParticles(WorldAccess world, BlockPos pos, int count) {
        if (count == 0) {
            count = 15;
        }

        double d = 0.5D;
        double g = 1;

        world.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
        Random random = world.getRandom();

        for(int i = 0; i < count; ++i) {
            double h = random.nextGaussian() * 0.02D;
            double j = random.nextGaussian() * 0.02D;
            double k = random.nextGaussian() * 0.02D;
            double l = 0.5D - d;
            double m = (double)pos.getX() + l + random.nextDouble() * d * 2.0D;
            double n = (double)pos.getY() + random.nextDouble() * g;
            double o = (double)pos.getZ() + l + random.nextDouble() * d * 2.0D;
            if (!world.getBlockState(BlockPos.ofFloored(m, n, o).down()).isAir()) {
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, m, n, o, h, j, k);
            }
        }
    }
}
