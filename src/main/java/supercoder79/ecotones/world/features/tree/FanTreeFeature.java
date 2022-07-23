package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.util.layer.Layer;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.tree.root.RootLayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FanTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    public FanTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        int[][] roots = RootLayers.create(world.getSeed()).operate(pos.getX(), pos.getZ(), 3, 3);

        // Generate height
        // TODO: scale
        int height = 7 + random.nextInt(4);
        List<BlockPos> leafNodes = new ArrayList<>();

        // Generate roots
        for (int x1 = 0; x1 < roots.length; x1++) {
            for (int z1 = 0; z1 < roots[x1].length; z1++) {
                for (int y = 0; y < roots[x1][z1]; y++) {
                    // TODO: mutable
                    world.setBlockState(pos.add(x1 - 1, y, z1 - 1), config.woodState, 3);
                }
            }
        }

        for (int i = 0; i < height; i++) {
            BlockPos local = pos.up(i);

            world.setBlockState(local, config.woodState, 3);

            double progress = ((double)i / height);

            // If we're about halfway up the tree, generate branches
            if (progress > 0.4 && random.nextInt(2) == 0) {
                double theta = random.nextDouble() * 2 * Math.PI;
                // Make taller branches smaller
                int branchLength = (progress > 0.7 ? 2 : 3) + random.nextInt(3);

                // Generate branch
                for (int j = 0; j < branchLength; j++) {
                    int dx = (int) (Math.cos(theta) * j);
                    int dz = (int) (Math.sin(theta) * j);
                    BlockPos branchLocal = local.add(dx, 0, dz);
                    world.setBlockState(branchLocal, config.woodState, 3);

                    // Generate a leaf layer here
                    if (j == branchLength - 1) {
                        leafNodes.add(branchLocal);
                    }
                }

                // Generate small branches if we're down the tree a bit more
            } else if (progress > 0.3 && random.nextInt(3) == 0) {
                Direction axis = FeatureHelper.randomHorizontal(random);
                BlockPos dir = local.offset(axis);
                world.setBlockState(dir, config.woodState, 3);

                for (Direction direction : Direction.values()) {
                    FeatureHelper.placeLeaves(context, dir.offset(direction));
                }
            }

            // Generate a leaf layer at the top
            if (i == height - 1) {
                leafNodes.add(local);
            }
        }

        // Generate leaf layers
        for (BlockPos node : leafNodes) {
            // Place 5x5 leaf layer
            for(int x = -2; x <= 2; x++) {
                for(int z = -2; z <= 2; z++) {
                    if (Math.abs(x) == 2 && Math.abs(z) == 2 && random.nextInt(3) != 0) {
                        continue;
                    }

                    FeatureHelper.placeLeaves(context, node.add(x, 0, z));
                }
            }

            // Place 3x3 leaf layer
            for(int x = -1; x <= 1; x++) {
                for(int z = -1; z <= 1; z++) {
                    if (Math.abs(x) == 1 && Math.abs(z) == 1 && random.nextInt(2) == 0) {
                        continue;
                    }

                    FeatureHelper.placeLeaves(context, node.add(x, 1, z));
                }
            }

            // Place top thin leaf
            FeatureHelper.placeLeaves(context, node.up(2));
        }

        return false;
    }
}
