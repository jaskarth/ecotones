package supercoder79.ecotones.features.tree;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import supercoder79.ecotones.features.AbstractTreeFeature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class BananaTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    public BananaTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
        super(function);
    }

    @Override
    protected boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        if (!AbstractTreeFeature.isDirtOrGrass(world, pos.down())) return false;
        setToDirt(world, pos.down());

//        System.out.println("Generated at " + "" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());

        generateTrunk(world, random, mutable, logPositions, box, config);
        generateLeaves(world, random, mutable.toImmutable().up(8), leavesPositions, box, config);

        return true;
    }

    private void generateTrunk(ModifiableTestableWorld world, Random random, BlockPos.Mutable pos, Set<BlockPos> logs, BlockBox box, TreeFeatureConfig config) {
        int height = 8;
        BlockPos pos1 = pos.toImmutable();
        for (int y = 0; y < height; y++) {
            this.setLogBlockState(world, random, pos1.up(y), logs, box, config);
        }
    }

    private void generateLeaves(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> leaves, BlockBox box, TreeFeatureConfig config) {
        this.setLeavesBlockState(world, random, pos, leaves, box, config);

        generateLeafSection(world, random, pos, leaves, box, config, Direction.EAST);

        generateLeafSection(world, random, pos, leaves, box, config, Direction.WEST);

        generateLeafSection(world, random, pos, leaves, box, config, Direction.NORTH);

        generateLeafSection(world, random, pos, leaves, box, config, Direction.SOUTH);
    }

    private void generateLeafSection(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> leaves, BlockBox box, TreeFeatureConfig config, Direction direction) {
        for (int i = 0; i < 3; i++) {
            this.setLeavesBlockState(world, random, pos.offset(direction, i), leaves, box, config);
        }
        pos.up();
        for (int i = 0; i < 2; i++) {
            this.setLeavesBlockState(world, random, pos.offset(direction, i+3).up(), leaves, box, config);
        }
    }
}
