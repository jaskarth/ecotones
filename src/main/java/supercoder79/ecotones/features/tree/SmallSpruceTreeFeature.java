package supercoder79.ecotones.features.tree;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.class_5138;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SmallSpruceTreeFeature extends Feature<DefaultFeatureConfig> {
    private static BlockState LEAF_STATE = Blocks.SPRUCE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1);

    public SmallSpruceTreeFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    public boolean generate(IWorld world, class_5138 arg, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.down()) == Blocks.GRASS_BLOCK.getDefaultState()) {

            //trunk
            for (int y = 0; y < 5; y++) {
                world.setBlockState(pos.up(y), Blocks.SPRUCE_LOG.getDefaultState(), 2);
            }

            //3x3 leaves
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    world.setBlockState(pos.add(x, 2, z), LEAF_STATE, 2);
                }
            }

            //leaves on the side of the trunk
            for (int y = 0; y < 2; y++) {
                world.setBlockState(pos.add(1, 3 + y, 0), LEAF_STATE, 2);
                world.setBlockState(pos.add(-1, 3 + y, 0), LEAF_STATE, 2);
                world.setBlockState(pos.add(0, 3 + y, 1), LEAF_STATE, 2);
                world.setBlockState(pos.add(0, 3 + y, -1), LEAF_STATE, 2);
            }

            //leaves at the top
            world.setBlockState(pos.up(5), LEAF_STATE, 2);
        }

        return true;
    }
}
