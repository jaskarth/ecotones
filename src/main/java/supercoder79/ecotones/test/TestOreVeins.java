package supercoder79.ecotones.test;

import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.util.vein.OreVein;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@DevOnly
public final class TestOreVeins {
    public static void main(String[] args) {
        Random random = new Random();
        OctaveNoiseSampler<OpenSimplexNoise> enabledNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 3, 512 + 256, 1, 1);
        OctaveNoiseSampler<OpenSimplexNoise> veinANoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 1, 128, 1, 1);
        OctaveNoiseSampler<OpenSimplexNoise> veinBNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 1, 128, 1, 1);
        OctaveNoiseSampler<OpenSimplexNoise> xNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 2, 512, 1, 1);
        OctaveNoiseSampler<OpenSimplexNoise> zNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 2, 512, 1, 1);

        HashMap<OreVein.Point, Integer> colors = new HashMap<>();
        colors.put(new OreVein.Point(0.2, -0.05), 0xedd5ca);
        colors.put(new OreVein.Point(-0.2, 0.1), 0xd19945);
        colors.put(new OreVein.Point(0.6, 0.4), 0xe3cb19);

        ImageDumper.dumpImage("orevein_enabled.png", 2048, (x, z) -> {
            if (enabledNoise.sample(x, z) > 0.2) {
                return 0xaaaaaa;
            }

            return 0;
        });

        int rY = random.nextInt(64);
        ImageDumper.dumpImage("orevein_gen.png", 2048, (x, z) -> {
            double veinA = veinANoise.sample(x, rY, z);
            double veinB = veinBNoise.sample(x, rY, z);

            if (isVein(veinA, veinB)) {
                return 0xaaaaaa;
            }

            return 0;
        });

        ImageDumper.dumpImage("orevein_colors.png", 2048, (x, z) -> {
            double xn = xNoise.sample(x, z);
            double zn = zNoise.sample(x, z);

            int col = 0;

            double dist = Double.MAX_VALUE;

            for (Map.Entry<OreVein.Point, Integer> e : colors.entrySet()) {
                double distHere = e.getKey().dist(xn, zn);

                if (distHere < dist) {
                    dist = distHere;
                    col = e.getValue();
                }
            }

            return col;
        });

        ImageDumper.dumpImage("orevein_colors_normalized.png", 2048, (x, z) -> {
            double xn = x / 1024.0;
            double zn = z / 1024.0;

            int col = 0;

            double dist = Double.MAX_VALUE;

            for (Map.Entry<OreVein.Point, Integer> e : colors.entrySet()) {
                double distHere = e.getKey().dist(xn, zn);

                if (distHere < dist) {
                    dist = distHere;
                    col = e.getValue();
                }
            }

            return col;
        });

        ImageDumper.dumpImage("orevein_all.png", 2048, (x, z) -> {
            double xn = xNoise.sample(x, z);
            double zn = zNoise.sample(x, z);

            int col = 0;

            double dist = Double.MAX_VALUE;

            for (Map.Entry<OreVein.Point, Integer> e : colors.entrySet()) {
                double distHere = e.getKey().dist(xn, zn);

                if (distHere < dist) {
                    dist = distHere;
                    col = e.getValue();
                }
            }

            if (enabledNoise.sample(x, z) > 0.2) {
                double veinA = veinANoise.sample(x, rY, z);
                double veinB = veinBNoise.sample(x, rY, z);

                if (isVein(veinA, veinB)) {
                    return col;
                }
            }

            return 0;
        });
    }

    private static boolean isVein(double veinA, double veinB) {
        double d = Math.abs(1.0D * veinA) - 0.08;
        double e = Math.abs(1.0D * veinB) - 0.08;
        return Math.max(d, e) < 0.0D;
    }
}
