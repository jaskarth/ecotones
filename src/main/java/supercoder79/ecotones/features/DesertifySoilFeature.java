package supercoder79.ecotones.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class DesertifySoilFeature extends Feature<DefaultFeatureConfig> {
    public DesertifySoilFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        world.setBlockState(pos.down(), Blocks.COARSE_DIRT.getDefaultState(), 3);

        if (random.nextInt(3) == 0)
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState(), 3);
        return true;
    }
}
