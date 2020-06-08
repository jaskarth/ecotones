package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SugarCaneFeature extends Feature<DefaultFeatureConfig> {

    public SugarCaneFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                if (world.getBlockState(pos.add(x, -1, z)) != Blocks.GRASS_BLOCK.getDefaultState()) return true;
            }
        }

        world.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 2);
        if (random.nextInt(3) == 0) {
            for (int i = 0; i < random.nextInt(4) + 1; i++) {
                world.setBlockState(pos.add(-1, i, 0), Blocks.SUGAR_CANE.getDefaultState(), 2);
            }
        }
        if (random.nextInt(3) == 0) {
            for (int i = 0; i < random.nextInt(4) + 1; i++) {
                world.setBlockState(pos.add(1, i, 0), Blocks.SUGAR_CANE.getDefaultState(), 2);
            }
        }
        if (random.nextInt(3) == 0) {
            for (int i = 0; i < random.nextInt(4) + 1; i++) {
                world.setBlockState(pos.add(0, i, -1), Blocks.SUGAR_CANE.getDefaultState(), 2);
            }
        }
        if (random.nextInt(3) == 0) {
            for (int i = 0; i < random.nextInt(4) + 1; i++) {
                world.setBlockState(pos.add(0, i, 1), Blocks.SUGAR_CANE.getDefaultState(), 2);
            }
        }

        return true;
    }
}
