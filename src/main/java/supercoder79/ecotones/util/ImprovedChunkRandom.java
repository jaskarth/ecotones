package supercoder79.ecotones.util;

import net.minecraft.world.gen.ChunkRandom;

/**
 * Improved version of Minecraft's ChunkRandom to handle seeds and entropy better.
 */
public class ImprovedChunkRandom extends ChunkRandom {
    @Override
    public long setPopulationSeed(long worldSeed, int blockX, int blockZ) {
        blockX = blockX >> 4;
        blockZ = blockZ >> 4;
        this.setSeed(worldSeed);
        long a = this.nextLong() | 1L;
        long b = this.nextLong() | 1L;
        long c = this.nextLong() | 1L;
        long result = (a * blockX * blockX * blockX + b * blockZ * blockZ + c) ^ worldSeed;
        this.setSeed(result);
        return result;
    }

    // provides more entropy than normal but is not strictly required
    public long setPopulationSeed(long worldSeed, int blockX, int blockZ, double scale) {
        blockX = blockX >> 4;
        blockZ = blockZ >> 4;
        this.setSeed(worldSeed);
        long a = this.nextLong() | 1L;
        long b = this.nextLong() | 1L;
        long c = this.nextLong() | 1L;
        long result = (a * blockX * blockX * blockX + b * blockZ * blockZ + (c ^ Double.doubleToLongBits(scale))) ^ worldSeed;
        this.setSeed(result);
        return result;
    }

    @Override
    public long setDecoratorSeed(long populationSeed, int index, int step) {
        this.setSeed(populationSeed);
        long a = this.nextLong() | 1L;
        long b = this.nextLong() | 1L;
        long c = this.nextLong() | 1L;
        long d = this.nextLong() | 1L;

        long result = (a * index * index * index + b * c * a + (step + d) * c) ^ populationSeed;
        this.setSeed(result);
        return result;
    }

    @Override
    public long setCarverSeed(long worldSeed, int chunkX, int chunkZ) {
        this.setSeed(worldSeed);
        long l = this.nextLong() | 1L;
        long m = this.nextLong() | 1L;
        long n = (long)chunkX * l ^ (long)chunkZ * m ^ worldSeed;
        this.setSeed(n);
        return n;
    }

    public void setLayerSeed(long worldSeed, int x, int z, int index) {
        this.setSeed(worldSeed);
        long a = this.nextLong();
        long b = this.nextLong();
        long c = this.nextLong();
        setSeed((a * x * x * x + b * z * z + (index + 1) * c) ^ worldSeed);
    }
}
