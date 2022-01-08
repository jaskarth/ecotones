package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class WideShrubFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {

    public WideShrubFeature(Codec<SimpleTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        SimpleTreeFeatureConfig config = context.getConfig();

        //grab data from the decorator
        if (pos instanceof DataPos) {
            DataPos data = ((DataPos)pos);
            if (data.isLikelyInvalid) {
                return false;
            }
        }

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return true;
        }

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
                if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                    continue;
                }

                trySet(world, pos.add(x, 0 , z), config.leafState);
            }
        }

        return true;
    }

    protected void trySet(WorldAccess world, BlockPos pos, BlockState state) {
        if (!world.getBlockState(pos).getMaterial().isSolid()) {
            world.setBlockState(pos, state, 2);
        }
    }
}
