package supercoder79.ecotones.features.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class HazelFoliagePlacer extends FoliagePlacer {
    public HazelFoliagePlacer(int radius, int radiusRandom) {
        super(radius, radiusRandom, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
    }

    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int i, int j, int k, BlockPos pos, Set<BlockPos> positions) {
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
            this.generate(world, random, config, i, pos, l, m, positions);
        }
    }

    public int getRadius(Random random, int i, int j, BranchedTreeFeatureConfig config) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    protected boolean isInvalidForLeaves(Random random, int i, int j, int k, int l, int m) {
        return Math.abs(j) == m && Math.abs(l) == m;
    }

    public int getRadiusForPlacement(int i, int j, int k, int l) {
        return l <= 1 ? 0 : 2;
    }
}