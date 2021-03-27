package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.config.PatchFeatureConfig;

import java.util.Random;

public class PatchFeature extends Feature<PatchFeatureConfig> {
    public PatchFeature(Codec<PatchFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<PatchFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        PatchFeatureConfig config = context.getConfig();

        int radius = config.radius.getValue(random);
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(new ChunkRandom(random.nextLong()), -4, 1.0);

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

                    // Replace target state
                    if (world.getBlockState(local).isOf(config.target)) {
                        world.setBlockState(local, config.state, 3);
                    }
                }
            }
        }

        return true;
    }
}
