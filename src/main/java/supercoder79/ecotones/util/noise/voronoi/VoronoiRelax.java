package supercoder79.ecotones.util.noise.voronoi;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.CheckedRandom;

// Voronoi with a point relaxation algorithm
public final class VoronoiRelax {
    private final long seed;
    private final ChunkRandom random = new ChunkRandom(new CheckedRandom(0));

    public VoronoiRelax(long seed) {
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
                double xCenter = ((this.random.nextDouble() - this.random.nextDouble()) * 0.5 + 0.5) + xStart + x1;
                double zCenter = ((this.random.nextDouble() - this.random.nextDouble()) * 0.5 + 0.5) + zStart + z1;

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
}
