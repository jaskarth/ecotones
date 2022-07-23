package supercoder79.ecotones.world.tree.gen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.world.StructureWorldAccess;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.EcotonesData;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.tree.GeneratedTreeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarrenTreeGenerator implements TreeGenerator {
    public static final BarrenTreeGenerator INSTANCE = new BarrenTreeGenerator();

    @Override
    public GeneratedTreeData generate(StructureWorldAccess world, BlockPos pos, Random random, DataHolder data, SimpleTreeFeatureConfig config) {
        world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 3);

        int height = 3 + random.nextInt(3);
        double soilQuality = data.get(EcotonesData.SOIL_QUALITY, pos.getX(), pos.getZ());

        List<BlockPos> leaves = new ArrayList<>();

        height += (int)(soilQuality * 3.5);

        int leafStart = (int) Math.max(1, height * 0.25);

        for (int y = 0; y < height; y++) {
            BlockPos log = pos.up(y);

            world.setBlockState(log,config.woodState, 3);

            // Place leaves after the trunk are ends
            if (y > leafStart) {

                // 1 - [2 - 4]
                int count = 1 + random.nextInt(3 + (int)(soilQuality * 2.5));
                for (int i = 0; i < count; i++) {
                    Direction direction = Direction.Type.HORIZONTAL.random(new CheckedRandom(random.nextLong()));
                    BlockPos center = log.offset(direction);
                    placeLeaves(world, random, center, 0.6 + (soilQuality / 2.5), leaves, config);
                }
            }

            // Ensures top of tree is always leaves
            if (height - 2 < y) {
                placeLeaves(world, random, log, 1.0, leaves, config);
            }
        }

        return new GeneratedTreeData(ImmutableList.of(), leaves);
    }

    private static void placeLeaves(StructureWorldAccess world, Random random, BlockPos pos, double chance, List<BlockPos> leaves, SimpleTreeFeatureConfig config) {
        if (world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, config.leafState, 3);
            leaves.add(pos);
        }

        for (Direction dir : Direction.values()) {
            if (random.nextDouble() < chance) {
                BlockPos leaf = pos.offset(dir);
                if (world.getBlockState(leaf).isAir()) {
                    world.setBlockState(leaf, config.leafState, 3);
                    leaves.add(leaf);
                }
            }
        }
    }
}
