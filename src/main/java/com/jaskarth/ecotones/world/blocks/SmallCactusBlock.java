package com.jaskarth.ecotones.world.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import com.jaskarth.ecotones.world.items.EcotonesItems;

import java.util.List;

public class SmallCactusBlock extends EcotonesPlantBlock {
    public static BooleanProperty FRUITING = BooleanProperty.of("fruiting");

    protected SmallCactusBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FRUITING, false));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        return (downState == Blocks.SAND.getDefaultState() || downState == Blocks.RED_SAND.getDefaultState());
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.slowMovement(state, new Vec3d(0.8, 0.75, 0.8));
            if (!world.isClient && entity instanceof PlayerEntity && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double dX = Math.abs(entity.getX() - entity.lastRenderX);
                double dZ = Math.abs(entity.getZ() - entity.lastRenderZ);
                if (dX >= 0.003 || dZ >= 0.003) {
                    entity.damage(entity.getDamageSources().cactus(), 0.5F);
                }
            }
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            if (state.get(FRUITING)) {
                world.setBlockState(pos, state.with(FRUITING, false));
                dropStack(world, pos, new ItemStack(EcotonesItems.CACTUS_FRUIT, 1 + world.getRandom().nextInt(2)));

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // TODO: soil quality based chance
        if (!state.get(FRUITING) && random.nextInt(5) == 0) {
            world.setBlockState(pos, state.with(FRUITING, true));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FRUITING);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        if (state.get(FRUITING)) {
            return ImmutableList.of(new ItemStack(EcotonesItems.CACTUS_FRUIT, 1 + builder.getWorld().getRandom().nextInt(2)));
        } else {
            return ImmutableList.of();
        }
    }
}
