package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.util.TreeHelper;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.tree.trait.EcotonesTreeTraits;
import supercoder79.ecotones.world.tree.trait.oak.OakTrait;
import supercoder79.ecotones.world.tree.trait.oak.DefaultOakTrait;
import supercoder79.ecotones.world.treedecorator.LeafVineTreeDecorator;

import java.util.*;
import java.util.function.BiConsumer;

//creates a branching trunk then places leaves
public class BranchingOakTreeFeature extends EcotonesFeature<TreeGenerationConfig> {

    public BranchingOakTreeFeature(Codec<TreeGenerationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<TreeGenerationConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        ChunkGenerator generator = context.getGenerator();
        TreeGenerationConfig config = context.getConfig();

        //ensure spawn
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        // initialize data
        int maxHeight = 9;
        OakTrait trait = DefaultOakTrait.INSTANCE;

        if (pos instanceof DataPos) {
            DataPos data = (DataPos)pos;
            if (data.isLikelyInvalid) return false;

            maxHeight = data.maxHeight;

            if (generator instanceof EcotonesChunkGenerator) {
                trait = EcotonesTreeTraits.OAK.get((EcotonesChunkGenerator) generator, pos);
            }
        }

        // Scale height
        maxHeight = trait.scaleHeight(maxHeight);

        List<BlockPos> leafPlacementNodes = new ArrayList<>();

        branch(world, pos, random, (float) (random.nextDouble() * 2 * Math.PI), (float) trait.getPitch(random), maxHeight, 0, leafPlacementNodes, trait, config);

        growLeaves(world, random, leafPlacementNodes, trait, config);

        return false;
    }

    private void growLeaves(StructureWorldAccess world, Random random, List<BlockPos> leafPlacementNodes, OakTrait trait, TreeGenerationConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos node : leafPlacementNodes) {
            trait.generateLeaves(world, node, random, leaves, config);
        }

        if (config.generateVines) {
            BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

            TreeDecorator.Generator generator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), Set.of(), new HashSet<>(leaves), Set.of());
            new LeafVineTreeDecorator(3, 4, 2).generate(generator);
            new TrunkVineTreeDecorator().generate(generator);
        }
    }

    private void branch(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, int depth, List<BlockPos> leafPlacementNodes, OakTrait trait, TreeGenerationConfig config) {
        int height = maxHeight / config.branchingFactor;

        // add some extra length to the last branch
        if (depth == (maxHeight / config.branchingFactor) - 1) {
            height += random.nextInt(4);
        }

        for (int i = 0; i < height; i++) {
            BlockPos local = startPos.add(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i);

            //if the tree hits a solid block, stop the branch
            if (TreeHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            } else {
                break;
            }

            //place thick trunk if the tree is big enough
            if (((maxHeight / config.branchingFactor) - depth) > config.thickTrunkDepth && trait.generateThickTrunk()) {
                world.setBlockState(local.up(), config.woodState, 0);
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockPos local2 = local.offset(direction);
                    world.setBlockState(local2, config.woodState, 0);
                }
            }

            if (i == height - 1) {
                //test for last branch, then set leaves
                if (depth == (maxHeight / config.branchingFactor) - 1) {
                    leafPlacementNodes.add(local);
                    break;
                }

                //test upwards to ensure we have sky light
                BlockPos.Mutable mutable = local.mutableCopy();
                boolean shouldNotBranch = false;
                for (int y = local.getY() + 1; y < 256; y++) {
                    mutable.setY(y);
                    if (!TreeHelper.canLogReplace(world, mutable)) {
                        shouldNotBranch = true;
                        break;
                    }
                }

                //break if non opaque blocks were found
                if (shouldNotBranch) {
                    break;
                }

                //branch in approximately opposite directions
                double maxYaw = (Math.PI * config.pitchChange);
                double yaw1 = random.nextDouble() - 0.5;
                double yaw2 = -yaw1;

                double maxPitch = (Math.PI * config.yawChange);
                double pitch1 = random.nextDouble() - 0.5;
                double pitch2 = -pitch1;

                // Branch based on genetic traits
                boolean didBranch = false;
                if (random.nextDouble() < trait.branchChance()) {
                    didBranch = true;
                    branch(world, local, random, (float) (yaw + (yaw1 * maxYaw)), (float) (pitch + (pitch1 * maxPitch)), maxHeight, depth + 1, leafPlacementNodes, trait, config);
                }

                if (random.nextDouble() < trait.branchChance()) {
                    didBranch = true;
                    branch(world, local, random, (float) (yaw + (yaw2 * maxYaw)), (float) (pitch + (pitch2 * maxPitch)), maxHeight, depth + 1, leafPlacementNodes, trait, config);
                }

                // If no branches, then add leaves here
                if (!didBranch) {
                    leafPlacementNodes.add(local);
                }
            }
        }
    }
}
