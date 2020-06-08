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

public class PlaceCactiFeature extends Feature<DefaultFeatureConfig> {

    public PlaceCactiFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        world.setBlockState(pos.down(), Blocks.SAND.getDefaultState(), 3);

        //75% chance of base
        if (random.nextInt(4) > 0) {
            world.setBlockState(pos, Blocks.CACTUS.getDefaultState(), 3);

            //66% chance of middle
            if (random.nextInt(3) > 0) {
                world.setBlockState(pos.up(), Blocks.CACTUS.getDefaultState(), 3);

                //50% chance of top
                if (random.nextInt(2) == 0) {
                    world.setBlockState(pos.up(2), Blocks.CACTUS.getDefaultState(), 3);
                }
            }
        }
        return true;
    }
}
