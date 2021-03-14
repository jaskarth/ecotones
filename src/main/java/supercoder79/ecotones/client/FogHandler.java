package supercoder79.ecotones.client;

import supercoder79.ecotones.util.noise.OpenSimplexNoise;

public final class FogHandler {
    private static OpenSimplexNoise noise;

    public static void init(long seed) {
        noise = new OpenSimplexNoise(seed);
    }

    // TODO: fix problems with time sync

    public static double noiseFor(double time) {
        return noise.sample(time / 1000.0, 0);
    }

    public static double offsetFor(long time) {
        long localTime = (time + 4800) % 24000;
        long scaledTime = localTime - 4800;

        double offset = scaledTime / 4800.0;

        return Math.max(0, (-(offset * offset) + 1) * 0.45);
    }

    public static double multiplierFor(long time) {
        return multiplierFor(noiseFor(time), offsetFor(time));
    }

    public static double multiplierFor(double noise, double offset) {
        return Math.max(0.15, Math.min(1, 1 - (Math.max(0, noise + offset) * 0.85)));
    }
}
