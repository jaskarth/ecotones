package supercoder79.ecotones.test;

import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;

import java.util.Random;

@DevOnly
public class TestSandType {
    public static void main(String[] args) {
        ImprovedChunkRandom random = new ImprovedChunkRandom(new Random().nextLong());

        OctaveNoiseSampler<OpenSimplexNoise> noise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 3, 800, 1, 1);
        ImageDumper.dumpImage("sands.png", 1024, (x, z) -> {
            double noiseAt = noise.sample(x, z);
            noiseAt += (random.nextDouble(0.015) - random.nextDouble(0.015));

            return noiseAt > 0.35 ? ImageDumper.getIntFromColor(160, 220, 160) : ImageDumper.getIntFromColor(160, 160, 160);
        });
    }
}
