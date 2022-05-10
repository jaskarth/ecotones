package supercoder79.ecotones.world.river;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.source.SeedMixer;
import supercoder79.ecotones.world.layers.system.layer.util.CachingLayerSampler;

// VoronoiBiomeAccessType (FuzzyBiomeZoomer)
public final class RiverInterpolator {
    public static int sample(long seed, BlockPos pos, CachingLayerSampler rivers) {
        int i = pos.getX() - 2;
        int j = pos.getY() - 2;
        int k = pos.getZ() - 2;
        int l = i >> 2;
        int m = j >> 2;
        int n = k >> 2;
        double d = (double)(i & 3) / 4.0;
        double e = (double)(j & 3) / 4.0;
        double f = (double)(k & 3) / 4.0;
        int o = 0;
        double g = Double.POSITIVE_INFINITY;

        for(int p = 0; p < 8; ++p) {
            boolean bl = (p & 4) == 0;
            boolean bl2 = (p & 2) == 0;
            boolean bl3 = (p & 1) == 0;
            int q = bl ? l : l + 1;
            int r = bl2 ? m : m + 1;
            int s = bl3 ? n : n + 1;
            double h = bl ? d : d - 1.0;
            double t = bl2 ? e : e - 1.0;
            double u = bl3 ? f : f - 1.0;
            double v = method_38106(seed, q, r, s, h, t, u);
            if (g > v) {
                o = p;
                g = v;
            }
        }

        int p = (o & 4) == 0 ? l : l + 1;
        int bl = (o & 2) == 0 ? m : m + 1;
        int bl2 = (o & 1) == 0 ? n : n + 1;

        return rivers.sample(p, /*bl,*/ bl2);
    }

    private static double method_38106(long l, int i, int j, int k, double d, double e, double f) {
        long m = SeedMixer.mixSeed(l, (long)i);
        m = SeedMixer.mixSeed(m, (long)j);
        m = SeedMixer.mixSeed(m, (long)k);
        m = SeedMixer.mixSeed(m, (long)i);
        m = SeedMixer.mixSeed(m, (long)j);
        m = SeedMixer.mixSeed(m, (long)k);
        double g = method_38108(m);
        m = SeedMixer.mixSeed(m, l);
        double h = method_38108(m);
        m = SeedMixer.mixSeed(m, l);
        double n = method_38108(m);
        return MathHelper.square(f + n) + MathHelper.square(e + h) + MathHelper.square(d + g);
    }

    private static double method_38108(long l) {
        double d = (double)Math.floorMod(l >> 24, 1024) / 1024.0;
        return (d - 0.5) * 0.9;
    }
}
