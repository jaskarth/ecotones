package supercoder79.ecotones.features.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class HazelFoliagePlacer extends FoliagePlacer {
    private final int height;

    public HazelFoliagePlacer(int radius, int randomRadius, int offset, int randomOffset, int height) {
        super(radius, randomRadius, offset, randomOffset, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
        this.height = height;
    }

    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int trunkHeight, BlockPos pos, int foliageHeight, int radius, Set<BlockPos> positions) {
        int h = 0;
        int m = 1;
        int t = 3;
        for(int l = foliageHeight + getHeight(random, 0); l >= getHeight(random, 0); --l) {
            h++;
            if (h == t) {
                h = 0;
                m++;
                t--;
            }
            // this l - 3 is horrible but it works
            this.generate(world, random, config, pos, radius, l - 3, m, positions);
        }
    }

    public int getRadius(Random random, int baseHeight, BranchedTreeFeatureConfig config) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    public int getHeight(Random random, int i) {
        return this.height;
    }

    protected boolean isInvalidForLeaves(Random random, int i, int j, int k, int l, int m) {
        return Math.abs(j) == m && Math.abs(l) == m;
    }

    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }
}