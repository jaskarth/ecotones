package com.jaskarth.ecotones.world.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GeyserBlock extends Block {
    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;
    public GeyserBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(TRIGGERED, false));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos.up()).isOpaque()) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 6f, World.ExplosionSourceType.BLOCK);
            world.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, 200, random.nextDouble() * 0.005, 0.65, random.nextDouble() * 0.005, 0.5f);
        }

        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);

        world.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, 50, random.nextDouble() * 0.005, 1, random.nextDouble() * 0.005, 0.5f);
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, new Box(pos.add(-3, -2, -3), pos.add(3, 6, 3)));
        for (LivingEntity entity : list) {
            entity.addVelocity(0, 1.5, 0);
            entity.damage(entity.getDamageSources().generic(), 4);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        boolean isPowered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean isTriggered = state.get(TRIGGERED);

        if (isPowered && !isTriggered) {
            world.scheduleBlockTick(pos, this, 4);
            world.setBlockState(pos, state.with(TRIGGERED, true), 3);
        } else if (!isPowered && isTriggered) {
            world.setBlockState(pos, state.with(TRIGGERED, false), 3);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            if (world.getBlockState(pos.up()).getFluidState().isIn(FluidTags.WATER)) {
                world.addParticle(ParticleTypes.BUBBLE, false, pos.getX() + random.nextDouble(), pos.getY() + 1.1, pos.getZ() + random.nextDouble(), 0, 0.1, 0);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);

        if (!world.isClient) {
            Random random = world.getRandom();
            ((ServerWorld)world).spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, 50, random.nextDouble() * 0.005, 1, random.nextDouble() * 0.005, 0.5f);
        }
    }
}
