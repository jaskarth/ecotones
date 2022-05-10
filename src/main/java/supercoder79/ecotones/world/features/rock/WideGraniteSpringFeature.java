package supercoder79.ecotones.world.features.rock;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.random.ChunkRandom;
import net.minecraft.world.gen.random.SimpleRandom;
import supercoder79.ecotones.world.features.EcotonesFeature;

import java.util.Random;

public class WideGraniteSpringFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public WideGraniteSpringFeature(Codec<DefaultFeatureConfig> configCodec) {
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

        PerlinNoiseSampler sampler = new PerlinNoiseSampler(new ChunkRandom(new SimpleRandom(world.getSeed())));

        BlockPos.Mutable mutable = pos.mutableCopy();

        int height = 2 + random.nextInt(5);

        int radius = 6 + random.nextInt(3);
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

        // Radius goes from 1.0 to 0.6
        double radx = 1.0;
        double raddec = (1.0 / height) * 0.4;

        // Water radius goes from 0.05 to 0.5
        double wradx = 0.05;
        double wradinc = (1.0 / height) * 0.45;

        // Iterate from -1 to height to place a granite floor
        for (int y = -1; y < height; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dz = z / (double) radius;

                    double r2 = dx * dx + dz * dz;

                    int idx = (x + radius) * (radius * 2 + 1) + (z + radius);

                    double scaledRadius = radx + noise[idx];
                    double scaledWaterRadius = wradx + noise[idx];

                    if (r2 <= scaledRadius) {
                        if (y >= 1 && r2 <= scaledWaterRadius) {
                            world.setBlockState(mutable.set(pos, x, y, z), Blocks.WATER.getDefaultState(), 3);
                            world.createAndScheduleFluidTick(mutable.toImmutable(), Fluids.WATER, 0);
                        } else {
                            world.setBlockState(mutable.set(pos, x, y, z), Blocks.GRANITE.getDefaultState(), 3);
                        }
                    }
                }
            }

            if (y >= 0) {
                radx -= raddec;
                wradx += wradinc;
            }
        }

        return true;
    }
}
