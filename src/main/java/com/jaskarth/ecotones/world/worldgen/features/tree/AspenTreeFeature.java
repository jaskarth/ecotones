package com.jaskarth.ecotones.world.worldgen.features.tree;

import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.util.FeatureContext;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeature;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesChunkGenerator;
import com.jaskarth.ecotones.world.worldgen.tree.trait.EcotonesTreeTraits;
import com.jaskarth.ecotones.world.worldgen.tree.trait.aspen.AspenTrait;
import com.jaskarth.ecotones.world.worldgen.tree.trait.aspen.DefaultAspenTrait;

import java.util.Random;

public class AspenTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    public AspenTreeFeature(Codec<SimpleTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        AspenTrait trait = DefaultAspenTrait.INSTANCE;

        if (generator instanceof EcotonesChunkGenerator) {
            trait = EcotonesTreeTraits.ASPEN.get((EcotonesChunkGenerator) generator, pos);
        }

        double maxRadius = trait.maxRadius(random);
        int leafDistance = trait.leafDistance(random);
        double branchThreshold = trait.branchThreshold(random);

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int y = 0; y < 8; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            //add branch blocks
            if (maxRadius * trait.model(y / 7.f) > branchThreshold) {
                Direction.Axis axis = getAxis(random);
                world.setBlockState(mutable.offset(getDirection(axis, random)).up(leafDistance), config.woodState.with(Properties.AXIS, axis), 0);
            }

            mutable.move(Direction.UP);
        }

        if (config.leafState.isAir()) {
            return true;
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, leafDistance);

        for (int y = 0; y < 8; y++) {
            FeatureHelper.circle(mutable.mutableCopy(), maxRadius * (trait.model(y / 7.f)), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                }
            });
            mutable.move(Direction.UP);
        }

        return true;
    }

    private double radius(double x) {
        return -Math.pow(((1.4 * x) - 0.3), 2) + 1.2;
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
