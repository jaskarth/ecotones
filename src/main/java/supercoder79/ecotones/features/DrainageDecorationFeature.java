package supercoder79.ecotones.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.api.DrainageType;
import supercoder79.ecotones.util.DataPos;

import java.util.Random;
import java.util.function.Function;

public class DrainageDecorationFeature extends Feature<DefaultFeatureConfig> {
    public DrainageDecorationFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (pos instanceof DataPos) {
            DataPos data = ((DataPos)pos);
            if (data.isLikelyInvalid) return false;

            if (world.getBlockState(pos.down()) == Blocks.GRASS.getDefaultState()) {
                // too little - clay
                if (data.drainageType == DrainageType.TOO_LITTLE) {
                    world.setBlockState(pos.down(), Blocks.CLAY.getDefaultState(), 0);
                } else { // too much - sand
                    world.setBlockState(pos.down(), Blocks.SAND.getDefaultState(), 0);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
