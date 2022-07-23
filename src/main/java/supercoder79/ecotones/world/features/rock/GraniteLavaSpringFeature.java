package supercoder79.ecotones.world.features.rock;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.FeatureHelper;

import java.util.Random;

public class GraniteLavaSpringFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public GraniteLavaSpringFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());

        BlockState down = world.getBlockState(pos.down());
        if (!(down.isOf(Blocks.GRASS_BLOCK) || down.isOf(Blocks.GRANITE))) {
            return false;
        }

        world.setBlockState(pos.down(), Blocks.GRANITE.getDefaultState(), 3);

        for (Direction direction : FeatureHelper.HORIZONTAL) {
            world.setBlockState(pos.offset(direction), Blocks.GRANITE.getDefaultState(), 3);

            if (random.nextInt(6) == 0) {
                world.setBlockState(pos.offset(direction).up(), Blocks.GRANITE.getDefaultState(), 3);
            }
        }

        world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);

        return true;
    }
}
