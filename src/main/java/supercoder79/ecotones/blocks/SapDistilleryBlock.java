package supercoder79.ecotones.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.blocks.entity.EcotonesBlockEntities;
import supercoder79.ecotones.blocks.entity.SapDistilleryBlockEntity;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.items.EcotonesItems;

import java.util.Random;

public class SapDistilleryBlock extends BlockWithEntity {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);

    public SapDistilleryBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SapDistilleryBlockEntity) {
                SapDistilleryBlockEntity sapDistillery = (SapDistilleryBlockEntity) blockEntity;
                boolean interacted = false;

                if (player.getStackInHand(hand).isOf(Items.GLASS_BOTTLE)) {
                    if (sapDistillery.canFillBottle()) {
                        sapDistillery.reduceForBottle();
                        player.getStackInHand(hand).decrement(1);
                        player.getInventory().insertStack(new ItemStack(EcotonesItems.MAPLE_SYRUP));

                        interacted = true;
                    }
                }

                if (!interacted) {
                    player.openHandledScreen(sapDistillery);
                }
            }

            return ActionResult.CONSUME;
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            BlockEntity be = world.getBlockEntity(pos);

            if (be instanceof SapDistilleryBlockEntity) {
                if (((SapDistilleryBlockEntity)be).getSyrupAmount() > 0) {
                    double d = (double) pos.getX() + random.nextDouble();
                    double e = (double) pos.getY() + 0.8D;
                    double f = (double) pos.getZ() + random.nextDouble();
                    world.addParticle(EcotonesParticles.SYRUP_POP, d, e, f, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SapDistilleryBlockEntity && entity instanceof ItemEntity) {
                ((SapDistilleryBlockEntity)be).onCollision(world, (ItemEntity) entity);
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, EcotonesBlockEntities.SAP_DISTILLERY, SapDistilleryBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SapDistilleryBlockEntity(pos, state);
    }
}
