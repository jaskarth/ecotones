package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PumpkinFarmFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public PumpkinFarmFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());

        // TODO: make layer based approach

        // Check for water
        if (FeatureHelper.isSolidSurrounding(world, pos.down())) {
            world.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 3);

            for (Direction direction : FeatureHelper.HORIZONTAL) {
                // Make farmland 1/4 the time
                if (random.nextInt(4) > 0) {
                    BlockPos local = pos.offset(direction);

                    // Check if block is grass with air above
                    if (world.getBlockState(local.down()).isOf(Blocks.GRASS_BLOCK) && world.getBlockState(local).isAir()) {
                        world.setBlockState(local.down(), Blocks.FARMLAND.getDefaultState().with(Properties.MOISTURE, 7), 3);

                        // This is set to false when the attached pumpkin stem is placed
                        boolean shouldPlace = true;

                        if (random.nextInt(3) == 0) {
                            Direction pumpkinDir = FeatureHelper.randomHorizontal(random);

                            // Check if this direction won't go onto the water
                            if (pumpkinDir != direction.getOpposite()) {
                                if (isSoil(world.getBlockState(local.offset(pumpkinDir).down()))) {
                                    world.setBlockState(local.offset(pumpkinDir), Blocks.PUMPKIN.getDefaultState(), 3);
                                    world.setBlockState(local, Blocks.ATTACHED_PUMPKIN_STEM.getDefaultState().with(AttachedStemBlock.FACING, pumpkinDir), 3);
                                    shouldPlace = false;
                                }
                            }
                        }

                        if (shouldPlace) {
                            world.setBlockState(local, Blocks.PUMPKIN_STEM.getDefaultState().with(StemBlock.AGE, random.nextInt(7)), 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}
