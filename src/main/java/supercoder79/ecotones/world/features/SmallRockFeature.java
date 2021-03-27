package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;

import java.util.Random;

public class SmallRockFeature extends Feature<DefaultFeatureConfig> {
    public SmallRockFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();

        PerlinNoiseSampler sampler = new PerlinNoiseSampler(new ChunkRandom(world.getSeed()));

        if (!world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK)) {
            return false;
        }

        double radius = 0.95 + random.nextDouble() * 1.7;
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    int gX = x + pos.getX();
                    int gY = y + pos.getY();
                    int gZ = z + pos.getZ();
                    double rX = x / radius;
                    double rY = y / radius;
                    double rZ = z / radius;

                    if (rX * rX + rY * rY + rZ * rZ < 1 + sampler.sample(gX / 6.0, gY / 6.0, gZ / 6.0) * 0.3) {
                        world.setBlockState(mutable.set(gX, gY, gZ), selectState(random), 3);
                    }
                }
            }
        }

        return true;
    }

    private static BlockState selectState(Random random) {
        double val = random.nextDouble();
        if (val >= 0.98) {
            return Blocks.IRON_ORE.getDefaultState();
        } else if (val >= 0.9) {
            return Blocks.GRANITE.getDefaultState();
        } else if (val >= 0.8) {
            return Blocks.ANDESITE.getDefaultState();
        } else if (val >= 0.7) {
            return Blocks.DIORITE.getDefaultState();
        } else if (val >= 0.5) {
            return Blocks.COBBLESTONE.getDefaultState();
        }

        return Blocks.STONE.getDefaultState();
    }
}
