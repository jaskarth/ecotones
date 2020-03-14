package supercoder79.ecotones.features.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SmallPineFoliagePlacer extends FoliagePlacer {
    public SmallPineFoliagePlacer(int i, int j) {
        super(i, j, FoliagePlacerType.PINE_FOLIAGE_PLACER);
    }

    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int baseHeight, int trunkHeight, int radius, BlockPos pos, Set<BlockPos> leaves) {
        int i = 0;

        for(int j = baseHeight; j >= trunkHeight; --j) {
            this.generate(world, random, config, baseHeight, pos, j, i, leaves);
            if (i >= 1 && j == trunkHeight + 1) {
                --i;
            } else if (i < radius) {
                ++i;
            }
        }

    }

    public int getRadius(Random random, int baseHeight, int trunkHeight, BranchedTreeFeatureConfig config) {
        return 1;
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int x, int y, int z, int radius) {
        return Math.abs(x) == radius && Math.abs(z) == radius && radius > 0;
    }

    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius, int currentTreeHeight) {
        return currentTreeHeight <= 1 ? 0 : 2;
    }
}