package supercoder79.ecotones.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.util.DataPos;

import java.util.Random;
import java.util.function.Function;

public class ShrubFeature extends Feature<SimpleTreeFeatureConfig> {
    public ShrubFeature(Function<Dynamic<?>, ? extends SimpleTreeFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, SimpleTreeFeatureConfig config) {
        int randomHeight = 1;
        //grab data from the decorator
        if (pos instanceof DataPos) {
            DataPos data = ((DataPos)pos);
            randomHeight = data.maxHeight;
            if (data.isLikelyInvalid) return false;
        }
        //TODO: lower leaf count in < 0.1 drainage areas

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;
        int height = random.nextInt(randomHeight) + 1;
        for (int i = 0; i < height; i++) {
            world.setBlockState(pos.add(0, i, 0), config.woodState, 2);
        }

        trySet(world, pos.add(0, height, 0), config.leafState);

        trySet(world, pos.add(0, height-1, 1), config.leafState);
        trySet(world, pos.add(0, height-1, -1), config.leafState);
        trySet(world, pos.add(-1, height-1, 0), config.leafState);
        trySet(world, pos.add(1, height-1, 0), config.leafState);
        return true;
    }

    protected void trySet(IWorld world, BlockPos pos, BlockState state) {
        if (!world.getBlockState(pos).getMaterial().isSolid()) world.setBlockState(pos, state, 2);
    }
}
