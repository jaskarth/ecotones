package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.config.WaterFeatureConfig;

import java.util.ArrayList;
import java.util.List;

public class PlaceWaterFeature extends EcotonesFeature<WaterFeatureConfig> {
    public PlaceWaterFeature(Codec<WaterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<WaterFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        WaterFeatureConfig config = context.getConfig();

        BlockPos.Mutable mutable = pos.mutableCopy();

        List<BlockPos> toReplace = new ArrayList<>();

        for (int i = 0; i < random.nextInt(4) + 16; i++) {
            //pick random position
            mutable.set(pos, random.nextInt(config.spread) - random.nextInt(config.spread), 0, random.nextInt(config.spread) - random.nextInt(config.spread));
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ()) - 1;
            mutable.setY(y);

            toReplace.clear();

            // test for target and then continue
            if (config.targets.contains(world.getBlockState(mutable))) {
                // ensure there is a block under the water
                if (!world.getBlockState(mutable.down()).isOpaque()) {
                    continue;
                }

                boolean canSpawn = true;

                // check surrounding for non-opaque blocks
                BlockPos origin = mutable.toImmutable();
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    mutable.set(origin, direction);

                    if (!world.getBlockState(mutable).isOpaque()) {
                        if (!world.getFluidState(mutable).isIn(FluidTags.WATER)) {
                            canSpawn = false;

                            break;
                        }

                    } else {
                        toReplace.add(mutable.toImmutable());
                    }
                }

                if (canSpawn) {
                    world.setBlockState(mutable.set(origin), Blocks.WATER.getDefaultState(), 3);

                    if (config.surroundings.isPresent()) {
                        for (BlockPos replacement : toReplace) {
                            world.setBlockState(replacement, config.surroundings.get().get(random, replacement), 3);
                        }

                        // Set state below
                        world.setBlockState(mutable.down(), config.surroundings.get().get(random, mutable.down()), 3);
                    }
                }
            }
        }

        return false;
    }
}
