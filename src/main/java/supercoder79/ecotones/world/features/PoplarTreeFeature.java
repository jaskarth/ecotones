package supercoder79.ecotones.world.features;

import com.mojang.datafixers.Dynamic;
import com.terraformersmc.terraform.util.Shapes;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class PoplarTreeFeature extends Feature<SimpleTreeFeatureConfig> {
    public PoplarTreeFeature(Function<Dynamic<?>, ? extends SimpleTreeFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, SimpleTreeFeatureConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        double maxRadius = 2.6 + ((random.nextDouble() - 0.5) * 0.2);
        int leafDistance = random.nextInt(3) + 2;

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int y = 0; y < 12; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            //add branch blocks
            if (maxRadius * radius(y / 11.f) > 2.3) {
                Direction.Axis axis = getAxis(random);
                world.setBlockState(mutable.offset(getDirection(axis, random)).up(leafDistance), config.woodState.with(Properties.AXIS, axis), 0);
            }

            mutable.move(Direction.UP);
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, leafDistance);

        for (int y = 0; y < 12; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * radius(y / 11.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                }
            });
            mutable.move(Direction.UP);
        }

        return false;
    }

    private double radius(double x) {
        return (-2 * (x * x * x)) + (1.9 * x) + 0.2;
    }

    private Direction.Axis getAxis(Random random) {
        return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
    }

    private Direction getDirection(Direction.Axis axis, Random random) {
        if (axis == Direction.Axis.X) {
            return random.nextBoolean() ? Direction.EAST : Direction.WEST;
        } else {
            return random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
        }
    }
}
