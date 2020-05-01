package supercoder79.ecotones.world.features;

import com.mojang.datafixers.Dynamic;
import com.terraformersmc.terraform.util.Shapes;
import net.minecraft.block.Blocks;
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

public class BigShrubFeature extends Feature<SimpleTreeFeatureConfig> {
    public BigShrubFeature(Function<Dynamic<?>, ? extends SimpleTreeFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, SimpleTreeFeatureConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        double maxRadius = 1.75 + ((random.nextDouble() - 0.5) * 0.2);

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int y = 0; y < 4; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            mutable.move(Direction.UP);
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, 1);

        for (int y = 0; y < 5; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * radius(y / 5.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                }
            });
            mutable.move(Direction.UP);
        }

        return false;
    }

    private double radius(double x) {
        //TODO: refactor to not use a 4th degree polynomial
        return (-2.7 * (x * x * x * x) + (1.95 * x) + 0.7);
    }
}
