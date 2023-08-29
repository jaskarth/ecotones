package com.jaskarth.ecotones.client;

import net.minecraft.util.math.MathHelper;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;

public final class FogHandler {
    private static OpenSimplexNoise noise;

    public static void init(long seed) {
        noise = new OpenSimplexNoise(seed);
    }

    // TODO: fix problems with time sync

    public static double noiseFor(double time) {
        return noise.sample(time / 1200.0, 0) * 0.9;
    }

    public static double offsetFor(long time) {
        long localTime = (time + 4800) % 24000;
        long scaledTime = localTime - 4800;

        double offset = scaledTime / 4800.0;

        long rawLocalTime = time % 24000L;
        double o = 0;
        if (rawLocalTime > 12000) {
            o = 0.75;
            // Night fade in
            o = MathHelper.clampedLerp(0, o, (rawLocalTime - 12000) / 2000.0);

            // Night fade out
            o = MathHelper.clampedLerp(0, o, (24000 - rawLocalTime) / 2000.0);
        }

        return Math.max(0, (-(offset * offset) + 1) * 0.55) + o;
    }

    public static double multiplierFor(long time) {
        return multiplierFor(noiseFor(time), offsetFor(time));
    }

    public static double multiplierFor(double noise, double offset) {
        return Math.max(0.45, Math.min(1, 1 - (Math.max(0, noise + offset) * 0.6)));
    }
}
