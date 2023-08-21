package supercoder79.ecotones.blocks;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.List;

public class BlueberryBushBlock extends PlantBlock implements Fertilizable {
    // 0 - Small bush
    // 1 - Medium bush
    // 2 - Grown bush
    // 3 - Flowering bush
    // 4 - Berry bush
    public static final IntProperty AGE = IntProperty.of("age", 0, 4);

    private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);
    private static final VoxelShape MEDIUM_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    protected BlueberryBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(EcotonesItems.BLUEBERRIES);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int age = state.get(AGE);
        if (age == 0) {
            return SMALL_SHAPE;
        } else if (age == 1) {
            return MEDIUM_SHAPE;
        } else {
            return LARGE_SHAPE;
        }
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 4;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(AGE);

        int growthChance = 5;
        ChunkGenerator generator = world.getChunkManager().getChunkGenerator();

        if (generator instanceof EcotonesChunkGenerator) {
            double soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX(), pos.getZ());
            double soilPh = ((EcotonesChunkGenerator)generator).getSoilPhAt(pos.getX(), pos.getZ());
            growthChance = growthChance(soilQuality, soilPh);
        }

        if (age < 4 && random.nextInt(growthChance) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, age + 1), 2);
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(AGE);
        boolean isGrown = age == 4;

        if (!isGrown && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (isGrown) {
            int baseCount = 1;
            int randomCount = 3;
            if (!world.isClient) {
                ChunkGenerator generator = ((ServerChunkManager) world.getChunkManager()).getChunkGenerator();
                if (generator instanceof EcotonesChunkGenerator) {
                    double soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX(), pos.getZ());
                    double soilPh = ((EcotonesChunkGenerator)generator).getSoilPhAt(pos.getX(), pos.getZ());
                    baseCount = baseCount(soilQuality, soilPh);
                    randomCount = randomCount(soilQuality, soilPh);
                }
            }

            // Get berries and ensure that at least one is dropped
            int berries = Math.max(1, baseCount + world.random.nextInt(randomCount));
            dropStack(world, pos, new ItemStack(EcotonesItems.BLUEBERRIES, berries));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);

            // Reset to grown
            world.setBlockState(pos, state.with(AGE, 2), 2);
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 4;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int age = Math.min(4, state.get(AGE) + random.nextInt(2) + 1);
        world.setBlockState(pos, state.with(AGE, age), 2);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        int baseCount = 1;
        int randomCount = 3;

        if (state.get(AGE) < 2) return ImmutableList.of();

        ServerWorld world = builder.getWorld();
        ChunkGenerator generator = world.getChunkManager().getChunkGenerator();

        if (generator instanceof EcotonesChunkGenerator) {
            Vec3d vec = builder.get(LootContextParameters.ORIGIN);

            double soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(vec.x, vec.z);
            double soilPh = ((EcotonesChunkGenerator)generator).getSoilPhAt(vec.x, vec.z);
            baseCount = baseCount(soilQuality, soilPh);
            randomCount = randomCount(soilQuality, soilPh);
        }

        return ImmutableList.of(new ItemStack(EcotonesItems.BLUEBERRIES, Math.max(1, baseCount + world.random.nextInt(randomCount))));
    }

    public static int baseCount(double soilQuality, double soilPh) {
        int baseCount = 0;
        if (soilQuality > 0.6) {
            baseCount++;
        }

        if (soilPh < -0.2) {
            baseCount++;
        }

        if (soilPh < -0.6) {
            baseCount++;
        }

        return baseCount;
    }

    public static int randomCount(double soilQuality, double soilPh) {
        int randomCount = 2;
        if (soilQuality > 0.5) {
            randomCount++;
        }

        if (soilQuality > 0.8) {
            randomCount++;
        }

        if (soilPh < 0) {
            randomCount++;
        }

        if (soilPh < -0.6) {
            randomCount++;
        }

        return randomCount;
    }

    private static int growthChance(double soilQuality, double soilPh) {
        int chance = 5;
        if (soilQuality > 0.65) {
            chance--;
        }

        if (soilPh < 0) {
            chance--;
        }

        if (soilPh < -0.7) {
            chance--;
        }

        return chance;
    }
}
