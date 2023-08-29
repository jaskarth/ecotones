package com.jaskarth.ecotones.world.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PodzolPatchFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public PodzolPatchFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        // TODO: customizable
        int radius = 2 + random.nextInt(4);
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(new ChunkRandom(new CheckedRandom(random.nextLong())), -4, 1.0);

        for(int x = -radius; x <= radius; x++) {
            for(int z = -radius; z <= radius; z++) {
                double dx = x / (double)radius;
                double dz = z / (double)radius;
                double rad = dx * dx + dz * dz;

                int worldX = pos.getX() + x;
                int worldZ = pos.getZ() + z;
                int topY = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, worldX, worldZ) - 1;

                double offset = noise.sample(worldX, topY, worldZ) * 0.55;
                offset += (random.nextDouble() - 0.5) * 1.5;

                // Noise + Random offsetted circle
                if (rad <= 1.0 + offset) {
                    BlockPos local = new BlockPos(worldX, topY, worldZ);

                    // Replace grass with podzol
                    if (world.getBlockState(local).isOf(Blocks.GRASS_BLOCK)) {
                        world.setBlockState(local, Blocks.PODZOL.getDefaultState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
