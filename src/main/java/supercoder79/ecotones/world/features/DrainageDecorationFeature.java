package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.api.DrainageType;
import supercoder79.ecotones.util.DataPos;

public class DrainageDecorationFeature extends Feature<DefaultFeatureConfig> {

    public DrainageDecorationFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();

        if (pos instanceof DataPos) {
            DataPos data = ((DataPos)pos);
            if (data.isLikelyInvalid) return false;

            if (world.getBlockState(pos.down()) == Blocks.GRASS_BLOCK.getDefaultState()) {
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
