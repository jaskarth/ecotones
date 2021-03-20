package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class DesertifySoilFeature extends Feature<DefaultFeatureConfig> {

    public DesertifySoilFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        world.setBlockState(pos.down(), Blocks.COARSE_DIRT.getDefaultState(), 3);

        //66% chance of dead bush
        if (random.nextInt(3) > 0) {
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState(), 3);
        }

        return true;
    }
}
