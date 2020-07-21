package supercoder79.ecotones.world.features.foliage;

import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;

import java.util.Random;
import java.util.Set;

/**
 * Slightly altered PineFoliagePlacer that makes large trees with thin leaf structures.
 */
public class SmallPineFoliagePlacer extends PineFoliagePlacer {

    public SmallPineFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l, m, n);
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int i, BlockBox blockBox) {
        for(int k = i; k >= i - foliageHeight; --k) {
            // topmost is a single block
            if (k == i) {
                this.generate(world, random, config, treeNode.getCenter(), 0, leaves, k, treeNode.isGiantTrunk(), blockBox);
            } else {
                // everything after is a +
                this.generate(world, random, config, treeNode.getCenter(), 1, leaves, k, treeNode.isGiantTrunk(), blockBox);
            }
        }
    }
}