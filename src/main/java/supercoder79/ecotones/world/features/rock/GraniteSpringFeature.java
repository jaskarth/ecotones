package supercoder79.ecotones.world.features.rock;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class GraniteSpringFeature extends Feature<DefaultFeatureConfig> {

    public GraniteSpringFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        BlockState down = world.getBlockState(pos.down());
        if (!(down.isOf(Blocks.GRASS_BLOCK) || down.isOf(Blocks.GRANITE))) {
            return false;
        }

        PerlinNoiseSampler sampler = new PerlinNoiseSampler(new ChunkRandom(world.getSeed()));

        BlockPos.Mutable mutable = pos.mutableCopy();

        int height = 4 + random.nextInt(4);
        int waterStart = height - random.nextInt(height - 2);

        int radius = 3 + random.nextInt(3);
        int centerY = world.getTopY(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());

        double[] noise = new double[(radius * 2 + 1) * (radius * 2 + 1)];

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                int gX = pos.getX() + x;
                int gZ = pos.getZ() + z;

                if (world.getTopY(Heightmap.Type.OCEAN_FLOOR, gX, gZ) < centerY) {
                    return false;
                }

                noise[(x + radius) * (radius * 2 + 1) + (z + radius)] = sampler.sample(gX / 4.0, 0, gZ / 4.0) * 0.3;
            }

        }

        // Radius goes from 1.0 to 0.3
        double radx = 1.0;
        double raddec = (1.0 / height) * 0.7;

        // Iterate from -1 to height to place a granite floor
        for (int y = -1; y < height; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dz = z / (double) radius;

                    double r2 = dx * dx + dz * dz;

                    double scaledRadius = radx + noise[(x + radius) * (radius * 2 + 1) + (z + radius)];

                    if (r2 <= scaledRadius) {
                        if (y >= waterStart && r2 <= scaledRadius - 0.395) {
                            world.setBlockState(mutable.set(pos, x, y, z), Blocks.WATER.getDefaultState(), 3);
                            world.getFluidTickScheduler().schedule(mutable.toImmutable(), Fluids.WATER, 0);
                        } else {
                            world.setBlockState(mutable.set(pos, x, y, z), Blocks.GRANITE.getDefaultState(), 3);
                        }
                    }
                }
            }

            if (y >= 0) {
                radx -= raddec;
            }
        }

        return true;
    }
}
