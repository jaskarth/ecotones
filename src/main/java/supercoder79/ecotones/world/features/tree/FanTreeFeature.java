package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FanTreeFeature extends Feature<SimpleTreeFeatureConfig> {
    public FanTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        SimpleTreeFeatureConfig config = context.getConfig();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        int height = 7 + random.nextInt(4);
        List<BlockPos> leafNodes = new ArrayList<>();

        for (Direction direction : FeatureHelper.HORIZONTAL) {
            if (random.nextInt(6) > 0) {
                world.setBlockState(pos.offset(direction), config.woodState, 3);

                if (random.nextInt(2) == 0) {
                    world.setBlockState(pos.offset(direction).up(), config.woodState, 3);
                }
            }
        }

        for (int i = 0; i < height; i++) {
            BlockPos local = pos.up(i);

            world.setBlockState(local, config.woodState, 3);

            double progress = ((double)i / height);

            if (progress > 0.4 && random.nextInt(2) == 0) {
                double theta = random.nextDouble() * 2 * Math.PI;
                int branchLength = (progress > 0.7 ? 3 : 2) + random.nextInt(3);

                for (int j = 0; j < branchLength; j++) {
                    int dx = (int) (Math.cos(theta) * j);
                    int dz = (int) (Math.sin(theta) * j);
                    BlockPos branchLocal = local.add(dx, 0, dz);
                    world.setBlockState(branchLocal, config.woodState, 3);

                    if (j == branchLength - 1) {
                        leafNodes.add(branchLocal);
                    }
                }

            } else if (progress > 0.3 && random.nextInt(3) == 0) {
                Direction axis = FeatureHelper.randomHorizontal(random);
                BlockPos dir = local.offset(axis);
                world.setBlockState(dir, config.woodState, 3);

                for (Direction direction : Direction.values()) {
                    FeatureHelper.placeLeaves(context, dir.offset(direction));
                }
            }

            if (i == height - 1) {
                leafNodes.add(local);
            }
        }

        for (BlockPos node : leafNodes) {
            for(int x = -2; x <= 2; x++) {
                for(int z = -2; z <= 2; z++) {
                    if (Math.abs(x) == 2 && Math.abs(z) == 2 && random.nextInt(3) != 0) {
                        continue;
                    }

                    FeatureHelper.placeLeaves(context, node.add(x, 0, z));
                }
            }

            for(int x = -1; x <= 1; x++) {
                for(int z = -1; z <= 1; z++) {
                    if (Math.abs(x) == 1 && Math.abs(z) == 1 && random.nextInt(2) == 0) {
                        continue;
                    }

                    FeatureHelper.placeLeaves(context, node.add(x, 1, z));
                }
            }

            FeatureHelper.placeLeaves(context, node.up(2));
        }

        return false;
    }
}
