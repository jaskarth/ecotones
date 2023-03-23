package supercoder79.ecotones.util.noise.voronoi;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.util.Vec2d;

// Simple voronoi diagram
public final class Voronoi {
    private final long seed;
    private final ImprovedChunkRandom random = new ImprovedChunkRandom(0);

    public Voronoi(long seed) {
        this.seed = seed;
    }

    public int get(double x, double z) {
        int xStart = MathHelper.floor(x);
        int zStart = MathHelper.floor(z);

        int chosenColor = 0;
        double minDist = Double.MAX_VALUE;
        for (int x1 = -1; x1 <= 1; x1++) {
            for (int z1 = -1; z1 <= 1; z1++) {
                this.random.setPopulationSeed(this.seed, xStart + x1, zStart + z1);
                double xCenter = this.random.nextDouble() + xStart + x1;
                double zCenter = this.random.nextDouble() + zStart + z1;

                double ax = x - xCenter;
                double az = z - zCenter;
                double dist = ax * ax + az * az;
                if (dist < minDist) {
                    minDist = dist;
                    chosenColor = this.random.nextInt();
                }
            }
        }

        return chosenColor;
    }

    // getCellPos(x / scale, z / scale, scale)
    public Vec2d getCellPos(double x, double z, double scale) {
        int xStart = MathHelper.floor(x);
        int zStart = MathHelper.floor(z);

        Vec2d vec = null;
        double minDist = Double.MAX_VALUE;

        for (int x1 = -1; x1 <= 1; x1++) {
            for (int z1 = -1; z1 <= 1; z1++) {
                this.random.setPopulationSeed(this.seed, xStart + x1, zStart + z1);
                double cx = this.random.nextDouble();
                double cz = this.random.nextDouble();
                double xCenter = cx + xStart + x1;
                double zCenter = cz + zStart + z1;

                double ax = x - xCenter;
                double az = z - zCenter;
                double dist = ax * ax + az * az;
                if (dist < minDist) {
                    minDist = dist;
                    // Multiplying by the scale is needed to normalize the grid to the size that it's needed in
                    vec = new Vec2d(xCenter * scale, zCenter * scale);
                }
            }
        }

        return vec;
    }
}
