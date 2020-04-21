package supercoder79.ecotones.features.tree;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SmallAcaciaTreeFeature extends Feature<DefaultFeatureConfig> {
    private static BlockState LEAF_STATE = Blocks.ACACIA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1);
    
    public SmallAcaciaTreeFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor arg, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.down()) == Blocks.GRASS_BLOCK.getDefaultState()) {
            world.setBlockState(pos, Blocks.ACACIA_LOG.getDefaultState(), 2);
            int offset = 0;
            if (random.nextInt(2) == 0) {
                world.setBlockState(pos.up(), Blocks.ACACIA_LOG.getDefaultState(), 2);
                offset++;
            }
            int xOffset = random.nextInt(3) - 1;
            int zOffset = random.nextInt(3) - 1;

            world.setBlockState(pos.add(xOffset, offset + 1, zOffset), Blocks.ACACIA_LOG.getDefaultState(), 2);
            world.setBlockState(pos.add(xOffset*2, offset + 2, zOffset*2), Blocks.ACACIA_LOG.getDefaultState(), 2);
            if (random.nextInt(2) == 0) {
                world.setBlockState(pos.add(xOffset*3, offset + 3, zOffset*3), Blocks.ACACIA_LOG.getDefaultState(), 2);
                setIfAir(world, pos.add((xOffset * 3), offset + 3, (zOffset*3) + 1), LEAF_STATE);
                setIfAir(world, pos.add((xOffset * 3), offset + 3, (zOffset*3) - 1), LEAF_STATE);
                setIfAir(world, pos.add((xOffset*3) - 1, offset + 3, (zOffset * 3)), LEAF_STATE);
                setIfAir(world, pos.add((xOffset*3) + 1, offset + 3, (zOffset * 3)), LEAF_STATE);
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        world.setBlockState(pos.add((xOffset * 3) + x, offset + 4, (zOffset*3) + z), LEAF_STATE, 2);
                    }
                }
            } else {
                setIfAir(world, pos.add((xOffset * 2), offset + 2, (zOffset*2) + 1), LEAF_STATE);
                setIfAir(world, pos.add((xOffset * 2), offset + 2, (zOffset*2) - 1), LEAF_STATE);
                setIfAir(world, pos.add((xOffset*2) - 1, offset + 2, (zOffset * 2)), LEAF_STATE);
                setIfAir(world, pos.add((xOffset*2) + 1, offset + 2, (zOffset * 2)), LEAF_STATE);
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        world.setBlockState(pos.add((xOffset * 2) + x, offset + 3, (zOffset*2) + z), LEAF_STATE, 2);
                    }
                }
            }

        }

        return true;
    }

    protected void setIfAir(IWorld world, BlockPos pos, BlockState state) {
        if (world.getBlockState(pos).isAir()) world.setBlockState(pos, state, 2);
    }
}
