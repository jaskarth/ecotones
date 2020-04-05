package supercoder79.ecotones.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.class_5138;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class PlaceCactiFeature extends Feature<DefaultFeatureConfig> {
    public PlaceCactiFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, class_5138 arg, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        world.setBlockState(pos.down(), Blocks.SAND.getDefaultState(), 3);

        if (random.nextInt(2) == 0) {
            world.setBlockState(pos, Blocks.CACTUS.getDefaultState(), 3);
            if (random.nextInt(2) == 0) {
                world.setBlockState(pos.up(), Blocks.CACTUS.getDefaultState(), 3);
            }
        }
        return true;
    }
}
