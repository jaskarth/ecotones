package supercoder79.ecotones.features.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class HazelFoliagePlacer extends FoliagePlacer {
    private final int field_23752;

    public HazelFoliagePlacer(int i, int j, int k, int l, int m) {
        super(i, j, k, l, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
        this.field_23752 = m;
    }

    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int i, BlockPos pos, int j, int k, Set<BlockPos> positions) {
        int h = 0;
        int m = 1;
        int t = 3;
        for(int l = i; l >= j; --l) {
            h++;
            if (h == t) {
                h = 0;
                m++;
                t--;
            }
            this.generate(world, random, config, pos, j, l, m, positions);
        }
    }

    public int getRadius(Random random, int baseHeight, BranchedTreeFeatureConfig config) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    public int method_26989(Random random, int i) {
        return this.field_23752;
    }

    protected boolean isInvalidForLeaves(Random random, int i, int j, int k, int l, int m) {
        return Math.abs(j) == m && Math.abs(l) == m;
    }

    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }
}