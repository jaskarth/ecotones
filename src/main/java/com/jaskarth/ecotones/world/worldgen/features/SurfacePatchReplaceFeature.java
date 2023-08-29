package com.jaskarth.ecotones.world.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.CheckedRandom;

import java.util.Random;

// Surfacebuilder 2!
public class SurfacePatchReplaceFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public SurfacePatchReplaceFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());

        int radius = 4 + random.nextInt(4);
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(new ChunkRandom(new CheckedRandom(random.nextLong())), -5, 1.0);

        for(int x = -radius; x <= radius; x++) {
            for(int z = -radius; z <= radius; z++) {
                double dx = x / (double)radius;
                double dz = z / (double)radius;
                double rad = dx * dx + dz * dz;

                int worldX = pos.getX() + x;
                int worldZ = pos.getZ() + z;
                int topY = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, worldX, worldZ);

                double offset = noise.sample(worldX, topY, worldZ) * 0.25;
                offset += (random.nextDouble() - 0.5) * 0.2;

                // Noise + Random offsetted circle
                if (rad <= 1.0 + offset) {
                    BlockPos local = new BlockPos(worldX, topY, worldZ);

                    BlockState startState = world.getBlockState(local);
                    if (!startState.isReplaceable()) {
                        continue;
                    }

                    boolean isWater = startState.getFluidState().isOf(Fluids.WATER);

                    local = local.down();

                    boolean needsSurface = !isWater;
                    int attempts = 0;
                    for (int y = 0; y < 4 + Math.abs(offset * 6); y++) {
                        // Replace target state
                        BlockState state = world.getBlockState(local);
                        if (state.isOf(Blocks.SAND) || state.isOf(Blocks.SANDSTONE)) {
                            if (needsSurface) {
                                world.setBlockState(local, Blocks.GRASS_BLOCK.getDefaultState(), 3);
                                needsSurface = false;
                            } else {
                                world.setBlockState(local, Blocks.DIRT.getDefaultState(), 3);
                            }
                        } else if (state.isAir()) {
                            y = 0;
                            needsSurface = true;
                        } else if (state.getFluidState().isOf(Fluids.WATER)) {
                            y = 0;
                            needsSurface = false;
                        }

                        attempts++;
                        if (attempts > 20) {
                            break;
                        }

                        local = local.down();
                    }
                }
            }
        }

        return true;
    }
}
