package com.jaskarth.ecotones.util;

import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.Arrays;

public class BiomeCache {
    private final long[] keys;
    private final Biome[] values;

    private final int mask;
    private final BiomeSource source;

    public BiomeCache(int size, BiomeSource source) {
        this.source = source;
        size = MathHelper.smallestEncompassingPowerOfTwo(size);
        this.mask = size - 1;

        this.keys = new long[size];
        Arrays.fill(this.keys, Long.MIN_VALUE);
        this.values = new Biome[size];
    }

    public Biome get(int x, int z) {
        long key = key(x, z);
        int idx = hash(key) & this.mask;

        // if the entry here has a key that matches ours, we have a cache hit
        if (this.keys[idx] == key) {
            return this.values[idx];
        }

        // cache miss: sample the source and put the result into our cache entry
        Biome sampled = source.getBiome(x, 0, z, null).value(); // TODO: null bad!
        this.values[idx] = sampled;
        this.keys[idx] = key;

        return sampled;
    }

    private static int hash(long key) {
        return (int) HashCommon.mix(key);
    }

    private static long key(int x, int z) {
        return ChunkPos.toLong(x, z);
    }
}