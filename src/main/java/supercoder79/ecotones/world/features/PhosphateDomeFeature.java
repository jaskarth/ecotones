package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.random.ChunkRandom;
import net.minecraft.world.gen.random.SimpleRandom;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.Random;

public class PhosphateDomeFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public PhosphateDomeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();

        if (!world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK)) {
            return false;
        }

        int radius = 4 + random.nextInt(5);

        int height = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                int heightHere = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + x, pos.getZ() + z);

                if (height - heightHere > radius) {
                    return false;
                }
            }
        }

        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(new ChunkRandom(new SimpleRandom(random.nextLong())), -4, 1.0);

        pos = pos.down(2 + (Math.max(0, radius - 4)));

        for (int y = -2; y <= radius; y++) {
            double chance = y > 2 ? 0.6 : ((y + 3) / 10.0);
            chance += (random.nextDouble(0.075) - random.nextDouble(0.075));

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dy = y / (double) radius;
                    double dz = z / (double) radius;

                    if (dx * dx + dy * dy + dz * dz < 1.0 + noise.sample(pos.getX() + x, pos.getY() + y, pos.getZ() + z) * 0.15) {
                        BlockPos local = pos.add(x, y, z);
                        boolean isAir = world.getBlockState(local).isAir();

                        if (random.nextDouble() < chance) {
                            world.setBlockState(local, EcotonesBlocks.PHOSPHATE_ORE.getDefaultState(), 3);
                        } else if (random.nextDouble() < 0.8 || isAir) {
                            world.setBlockState(local, Blocks.STONE.getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}
