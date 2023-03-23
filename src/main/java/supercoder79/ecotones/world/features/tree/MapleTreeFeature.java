package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.util.TreeHelper;
import supercoder79.ecotones.world.features.EcotonesFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapleTreeFeature extends EcotonesFeature<TreeGenerationConfig> {
    public MapleTreeFeature(Codec<TreeGenerationConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<TreeGenerationConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        TreeGenerationConfig config = context.getConfig();

        // Ensure spawn
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        int maxHeight = 6;
        if (pos instanceof DataPos) {
            DataPos data = (DataPos)pos;
            maxHeight = data.maxHeight;
            if (data.isLikelyInvalid) {
                return false;
            }
        }

        List<LeafNode> leafPlacementNodes = new ArrayList<>();

        trunk(world, pos, random, (float) (Math.PI / 2) + ((random.nextFloat() - 0.5f) / 2), (random.nextFloat() - 0.5f) / 1.75f, maxHeight, leafPlacementNodes, config);

        growLeaves(world, random, maxHeight, leafPlacementNodes, config);

        return false;
    }

    private void trunk(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, List<LeafNode> leafPlacementNodes, TreeGenerationConfig config) {
        float realPitch = pitch;
        pitch = 0;

        for (int i = 0; i < maxHeight; i++) {
            BlockPos local = startPos.add(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i);

            //if the tree hits a solid block, stop
            if (TreeHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 3);
            } else {
                break;
            }

            if (i > 2) {
                pitch = (float) MathHelper.clampedLerp(0, realPitch, (i - 2) / 2.5);
            }

            if (i > maxHeight / 2 && i < maxHeight - 2) {
                // Within canopy branches
                if (random.nextBoolean()) {
                    branch(world, local, random, leafPlacementNodes, config);
                }
            } else if (i > 1) {
                if (random.nextInt(3) == 0) {
                    branch(world, local, random, leafPlacementNodes, config);
                }
            }

            if (i == maxHeight - 1) {
                leafPlacementNodes.add(new LeafNode(LeafType.LARGE, local.up(2)));
            }
        }
    }

    private void branch(WorldAccess world, BlockPos startPos, Random random, List<LeafNode> leafPlacementNodes, TreeGenerationConfig config) {
        int length = 1 + random.nextInt(3);
        double theta = 2 * Math.PI * random.nextDouble();

        BlockPos lastPos = startPos;

        for (int i = 0; i < length; i++) {
            BlockPos local = startPos.add(Math.cos(theta) * i, i / 2.0, Math.sin(theta) * i);
            lastPos = local;

            if (TreeHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 3);
            } else {
                break;
            }
        }

        leafPlacementNodes.add(new LeafNode(LeafType.SMALL, lastPos));
    }

    private void growLeaves(WorldAccess world, Random random, int maxHeight, List<LeafNode> leafPlacementNodes, TreeGenerationConfig config) {
        for (LeafNode node : leafPlacementNodes) {
            if (node.leafType == LeafType.LARGE) {
                int leafCrownDepth = (maxHeight / 2) + random.nextInt(3);
                double offset = 0.15 + (1.75 / leafCrownDepth);

                double radius = 1.25;

                for(int i = 0; i < leafCrownDepth; i++) {
                    for(int x = -7; x <= 7; x++) {
                        for(int z = -7; z <= 7; z++) {
                            if (x * x + z * z <= radius * radius) {
                                BlockPos local = node.pos.add(x, -i, z);
                                if (world.getBlockState(local).isAir()) {
                                    world.setBlockState(local, config.leafState, 3);
                                }
                            }
                        }
                    }

                    radius += offset + random.nextDouble() / 3;
                }
            } else {
                for(int x = -1; x <= 1; x++) {
                    for(int z = -1; z <= 1; z++) {
                        if (Math.abs(x) == 1 && Math.abs(z) == 1) {
                            continue;
                        }

                        BlockPos local = node.pos.add(x, 1, z);
                        if (world.getBlockState(local).isAir()) {
                            world.setBlockState(local, config.leafState, 3);
                        }
                    }
                }

                for(int x = -2; x <= 2; x++) {
                    for(int z = -2; z <= 2; z++) {
                        if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                            continue;
                        }

                        BlockPos local = node.pos.add(x, 0, z);
                        if (world.getBlockState(local).isAir()) {
                            world.setBlockState(local, config.leafState, 3);
                        }
                    }
                }
            }
        }
    }

    private enum LeafType {
        LARGE,
        SMALL
    }

    private static final class LeafNode {
        private final LeafType leafType;
        private final BlockPos pos;

        private LeafNode(LeafType leafType, BlockPos pos) {
            this.leafType = leafType;
            this.pos = pos;
        }
    }
}
