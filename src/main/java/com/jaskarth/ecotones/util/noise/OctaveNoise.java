package com.jaskarth.ecotones.util.noise;

import com.google.gson.JsonObject;

import java.util.Random;

public final class OctaveNoise {
    private final OpenSimplexNoise[] octaves;
    private final double horizontalFrequency;
    private final double verticalFrequency;
    private final double amplitude;
    private final double lacunarity;
    private final double persistence;

    public OctaveNoise(int octaves, Random random, double horizontalFrequency, double verticalFrequency, double amplitude, double lacunarity, double persistence) {
        this.horizontalFrequency = horizontalFrequency;
        this.verticalFrequency = verticalFrequency;
        this.amplitude = amplitude;
        this.lacunarity = lacunarity;
        // Persistence is inverted to avoid division in sampling
        this.persistence = 1.0 / persistence;

        this.octaves = new OpenSimplexNoise[octaves];
        for (int i = 0; i < octaves; i++) {
            this.octaves[i] = new OpenSimplexNoise(random.nextLong());
        }
    }

    public double sample(double x, double z) {
        return this.sample(x, 0, z);
    }

    public double sample(double x, double y, double z) {
        double sum = 0;

        x /= this.horizontalFrequency;
        y /= this.verticalFrequency;
        z /= this.horizontalFrequency;

        double amplitude = this.amplitude;

        for (OpenSimplexNoise octave : this.octaves) {
            sum += octave.sample(x, y, z) * amplitude;

            amplitude *= this.persistence;

            x *= this.lacunarity;
            y *= this.lacunarity;
            z *= this.lacunarity;
        }

        return sum;
    }

    public static OctaveNoise deserialize(JsonObject json, long seed) {
        int octaves = json.get("octaves").getAsInt();
        double horizontalFrequency = json.get("horizontal_frequency").getAsDouble();
        double verticalFrequency = json.get("vertical_frequency").getAsDouble();
        double amplitude = json.get("amplitude").getAsDouble();
        double lacunarity = json.get("lacunarity").getAsDouble();
        double persistence = json.get("persistence").getAsDouble();
        int salt = json.has("salt") ? json.get("salt").getAsInt() : 0;

        return new OctaveNoise(octaves, new Random(seed + salt), horizontalFrequency, verticalFrequency, amplitude, lacunarity, persistence);
    }
}
