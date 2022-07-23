package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class SugarCaneFeature extends EcotonesFeature<DefaultFeatureConfig> {

    public SugarCaneFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());

        // Check the position for a 3x3 of grass underneath to grow the sugarcane
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (world.getBlockState(pos.add(x, -1, z)) != Blocks.GRASS_BLOCK.getDefaultState()) return false;
            }
        }

        // If the block 2 blocks down is transparent, it probably means we can't place water here.
        if (!world.getBlockState(pos.down(2)).isOpaque()) return false;

        // Set the base water block
        world.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 2);

        // Try to place a sugarcane column for every surrounding position
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (random.nextInt(3) == 0) { // Place a sugarcane column only 1/3 of the time
                int height = getHeight(random);

                BlockPos.Mutable mutable = pos.offset(direction).mutableCopy();

                // Place sugarcane going up to the height
                for (int y = 0; y < height; y++) {
                    world.setBlockState(mutable, Blocks.SUGAR_CANE.getDefaultState(), 3);

                    // Move onto the next block
                    mutable.move(Direction.UP);
                }
            }
        }

        return true;
    }

    private static int getHeight(Random random) {
        // Get a biased distribution of sugarcane height from 1-4
        return random.nextInt(random.nextInt(4) + 1) + 1;
    }
}
