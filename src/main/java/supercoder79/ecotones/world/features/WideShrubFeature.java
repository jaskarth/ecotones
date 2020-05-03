package supercoder79.ecotones.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class WideShrubFeature extends Feature<SimpleTreeFeatureConfig> {
    public WideShrubFeature(Function<Dynamic<?>, ? extends SimpleTreeFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, SimpleTreeFeatureConfig config) {
        //grab data from the decorator
        if (pos instanceof DataPos) {
            DataPos data = ((DataPos)pos);
            if (data.isLikelyInvalid) return false;
        }

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;
        world.setBlockState(pos, config.woodState, 0);
        BlockPos up = pos.up();

        // top +
        trySet(world, up, config.leafState);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            trySet(world, up.offset(direction), config.leafState);
        }

        // bottom 5x5 leaf layer
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) == 2 && Math.abs(z) == 2) continue;

                trySet(world, pos.add(x, 0 , z), config.leafState);
            }
        }

        return true;
    }

    protected void trySet(IWorld world, BlockPos pos, BlockState state) {
        if (!world.getBlockState(pos).getMaterial().isSolid()) world.setBlockState(pos, state, 2);
    }
}
