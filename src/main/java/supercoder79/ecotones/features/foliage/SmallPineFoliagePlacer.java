package supercoder79.ecotones.features.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SmallPineFoliagePlacer extends FoliagePlacer {
    private final int field_23755;
    private final int field_23756;

    public SmallPineFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l, FoliagePlacerType.PINE_FOLIAGE_PLACER);
        this.field_23755 = m;
        this.field_23756 = n;
    }

    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int baseHeight, BlockPos pos, int trunkHeight, int radius, Set<BlockPos> leaves) {
        int i = 0;
        int l = this.offset + random.nextInt(this.randomOffset + 1);

        for(int j = trunkHeight + l; j >= l; --j) {
            this.generate(world, random, config, pos, trunkHeight, j, i, leaves);
            if (i >= 1 && j == trunkHeight + 1) {
                --i;
            } else if (i < radius) {
                ++i;
            }
        }

    }

    @Override
    public int getHeight(Random random, int i) {
        return this.field_23755 + random.nextInt(this.field_23756 + 1);
    }

    public int getRadius(Random random, int baseHeight, BranchedTreeFeatureConfig config) {
        return 1;
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int x, int y, int z, int radius) {
        return Math.abs(x) == radius && Math.abs(z) == radius && radius > 0;
    }

    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }
}