package com.jaskarth.ecotones.client.sky;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import java.util.Random;

public final class SkyboxGenerator {
    public static void renderStars(BufferBuilder buffer) {
        Random random = new Random(10842L);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        for(int i = 0; i < 7500; ++i) {
            double d = (double)(random.nextFloat() * 2.0F - 1.0F);
            double e = (double)(random.nextFloat() * 2.0F - 1.0F);
            double f = (double)(random.nextFloat() * 2.0F - 1.0F);
            double g = getStarSize(random);
            double h = d * d + e * e + f * f;
            if (h < 1.0D) {
                h = 1.0D / Math.sqrt(h);
                d *= h;
                e *= h;
                f *= h;
                double j = d * 100.0D;
                double k = e * 100.0D;
                double l = f * 100.0D;
                double m = Math.atan2(d, f);
                double n = Math.sin(m);
                double o = Math.cos(m);
                double p = Math.atan2(Math.sqrt(d * d + f * f), e);
                double q = Math.sin(p);
                double r = Math.cos(p);
                double s = random.nextDouble() * 3.141592653589793D * 2.0D;
                double t = Math.sin(s);
                double u = Math.cos(s);

                for(int v = 0; v < 4; ++v) {
                    double x = (double)((v & 2) - 1) * g;
                    double y = (double)((v + 1 & 2) - 1) * g;
                    double aa = x * u - y * t;
                    double ab = y * u + x * t;
                    double ad = aa * q + 0.0D * r;
                    double ae = 0.0D * q - aa * r;
                    double af = ae * n - ab * o;
                    double ah = ab * n + ae * o;
                    buffer.vertex(j + af, k + ad, l + ah).next();
                }
            }
        }
    }

    private static double getStarSize(Random random) {
        return (0.05F + (random.nextFloat() * random.nextFloat() * 0.25F));
    }
}
