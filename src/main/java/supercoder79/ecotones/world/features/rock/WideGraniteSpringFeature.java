package supercoder79.ecotones.world.features.rock;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class WideGraniteSpringFeature extends Feature<DefaultFeatureConfig> {
    public WideGraniteSpringFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();

        BlockState down = world.getBlockState(pos.down());
        if (!(down.isOf(Blocks.GRASS_BLOCK) || down.isOf(Blocks.GRANITE))) {
            return false;
        }

        BlockPos.Mutable mutable = pos.mutableCopy();

        int height = 3 + random.nextInt(3);

        int radius = 6 + random.nextInt(2);
        int centerY = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + x, pos.getZ() + z) < centerY) {
                    return false;
                }
            }
        }

        // Radius goes from 1.0 to 0.6
        double radx = 1.0;
        double raddec = (1.0 / height) * 0.4;

        // Water radius goes from 0.1 to 0.5
        double wradx = 0.1;
        double wradinc = (1.0 / height) * 0.4;

        // Iterate from -1 to height to place a granite floor
        for (int y = -1; y < height; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dz = z / (double) radius;

                    double r2 = dx * dx + dz * dz;

                    if (r2 <= radx) {
                        if (y >= 1 && r2 <= wradx) {
                            world.setBlockState(mutable.set(pos, x, y, z), Blocks.WATER.getDefaultState(), 3);
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
