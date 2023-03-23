package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PlaceCactiFeature extends EcotonesFeature<DefaultFeatureConfig> {

    public PlaceCactiFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        world.setBlockState(pos.down(), Blocks.SAND.getDefaultState(), 3);

        if (random.nextBoolean()) { // Generate cactus half the time
            int height = getHeight(random);

            BlockPos.Mutable mutable = pos.mutableCopy();

            // Try placing cacti for the height, making sure that there is enough space to grow
            for (int y = 0; y < height; y++) {
                if (canGrow(world, mutable)) { // Place cacti if the space is cleared
                    world.setBlockState(mutable, Blocks.CACTUS.getDefaultState(), 3);
                } else {
                    break; // Stop placing cacti if the space is blocked
                }

                // Move onto the next block
                mutable.move(Direction.UP);
            }
        }

        return true;
    }

    private static int getHeight(Random random) {
        // Get a biased distribution of cactus height from 1-3
        return random.nextInt(random.nextInt(3) + 1) + 1;
    }

    private static boolean canGrow(ServerWorldAccess world, BlockPos pos) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (world.getBlockState(pos.offset(direction)).isOpaque()) {
                return false; // Opaque block, don't grow
            }
        }

        // No blocked spaces
        return true;
    }
}
