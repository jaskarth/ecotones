package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.Random;

public class BrushTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    private static final int[][] DELTAS = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    public BrushTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
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

        int height = random.nextInt(3) + 1;

        for (int y = 0; y < height; y++) {
            BlockPos local = pos.up(y);
            world.setBlockState(local, config.woodState, 3);

            for (int i = 0; i < 3; i++) {
                if (random.nextInt(2) == 0) {
                    FeatureHelper.placeLeaves(context, local.offset(FeatureHelper.randomHorizontal(random)));
                }
            }
        }

        // TODO: branch height automata
        int[] branchHeights = new int[4];
        for (int i = 0; i < 4; i++) {
            branchHeights[i] = random.nextInt(3) + 1;
        }

        for (int i = 0; i < DELTAS.length; i++) {
            for (int y = 0; y < branchHeights[i]; y++) {
                BlockPos local = pos.add(DELTAS[i][0], height + y, DELTAS[i][1]);
                world.setBlockState(local, config.woodState, 3);

                for (int j = 0; j < 3; j++) {
                    if (random.nextInt(2) == 0) {
                        FeatureHelper.placeLeaves(context, local.offset(FeatureHelper.randomHorizontal(random)));
                    }
                }
            }
        }

        for (int i = 0; i < DELTAS.length; i++) {
            generateLeaves(world, random, pos.add(DELTAS[i][0], height + branchHeights[i] - 1, DELTAS[i][1]), config);
        }

        return true;
    }

    private static void generateLeaves(StructureWorldAccess world, Random random, BlockPos pos, SimpleTreeFeatureConfig config) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                // place 3x3
                BlockPos local = pos.add(x, 0, z);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                }

                local = pos.add(x, -1, z);
                if (random.nextBoolean() && world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                }

                if (Math.abs(x) == 1 && Math.abs(z) == 1) {
                    continue;
                }

                // + shape on top
                local = pos.add(x, 1, z);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                }
            }
        }
    }
}
