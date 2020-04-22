package supercoder79.ecotones.features.foliage;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SmallPineFoliagePlacer extends FoliagePlacer {
    private final int height;
    private final int randomHeight;

    public SmallPineFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l, FoliagePlacerType.PINE_FOLIAGE_PLACER);
        this.height = m;
        this.randomHeight = n;
    }

    public <T> SmallPineFoliagePlacer(Dynamic<T> data) {
        this(data.get("radius").asInt(0), data.get("radius_random").asInt(0), data.get("offset").asInt(0), data.get("offset_random").asInt(0), data.get("height").asInt(0), data.get("height_random").asInt(0));
    }

    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig treeFeatureConfig, int trunkHeight, FoliagePlacer.class_5208 arg, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        int j = 0;

        for(int k = i; k >= i - foliageHeight; --k) {
            if (k == i) {
                this.generate(world, random, treeFeatureConfig, arg.method_27388(), 0, leaves, k + 1, arg.method_27390());
            }
            this.generate(world, random, treeFeatureConfig, arg.method_27388(), 1, leaves, k, arg.method_27390());
            if (j >= 1 && k == i - foliageHeight + 1) {
                --j;
            } else if (j < radius + arg.method_27389()) {
                ++j;
            }
        }

    }

    public int getRadius(Random random, int baseHeight) {
        return super.getRadius(random, baseHeight) + random.nextInt(baseHeight + 1);
    }

    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig treeFeatureConfig) {
        return this.height + random.nextInt(this.randomHeight + 1);
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return baseHeight == dz && dy == dz && dz > 0;
    }

    public <T> T serialize(DynamicOps<T> ops) {
        ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();
        builder.put(ops.createString("height"), ops.createInt(this.height)).put(ops.createString("height_random"), ops.createInt(this.randomHeight));
        return ops.merge(super.serialize(ops), ops.createMap(builder.build()));
    }
}