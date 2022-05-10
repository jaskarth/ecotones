package supercoder79.ecotones.util.noise.voronoi;

import supercoder79.ecotones.util.Vec2d;
import supercoder79.ecotones.util.Vec2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class VoronoiRaster {
    private final Voronoi voronoi;
    private final int startX;
    private final int startZ;
    private final int radius;
    private final double scale;

    // Output
    private final Map<Integer, List<Vec2i>> rastersByColor = new HashMap<>();
    private final Map<Vec2i, Integer> posToColor = new HashMap<>();
    private final Map<Integer, Vec2d> centersByColor = new HashMap<>();
    // TODO: voronoi graph? connect rasters together!

    public VoronoiRaster(long seed, int startX, int startZ, int radius, double scale) {
        this.voronoi = new Voronoi(seed);
        this.startX = startX;
        this.startZ = startZ;
        this.radius = radius;
        this.scale = scale;

        // Init

        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int z1 = -radius; z1 <= radius; z1++) {
                int color = this.voronoi.get((startX + x1) / scale, (startZ + z1) / scale);

                // Map by color
                this.rastersByColor.computeIfAbsent(color, a -> new ArrayList<>()).add(Vec2i.of(startX + x1, startZ + z1));
            }
        }

        // Discard outer edges
        for (int x1 = -radius; x1 <= radius; x1++) {
            int b = this.voronoi.get((startX + x1) / scale, (startZ - radius) / scale);
            int a = this.voronoi.get((startX + x1) / scale, (startZ + radius) / scale);

            this.rastersByColor.remove(a);
            this.rastersByColor.remove(b);
        }

        for (int z1 = -radius; z1 <= radius; z1++) {
            int b = this.voronoi.get((startX - radius) / scale, (startZ + z1) / scale);
            int a = this.voronoi.get((startX + radius) / scale, (startZ + z1) / scale);

            this.rastersByColor.remove(a);
            this.rastersByColor.remove(b);
        }

        // Discard circle TODO: configurable?
        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int z1 = -radius; z1 <= radius; z1++) {
                if (x1 * x1 + z1 * z1 > radius * radius) {
                    int color = this.voronoi.get((startX + x1) / scale, (startZ + z1) / scale);

                    this.rastersByColor.remove(color);
                }
            }
        }

        // Map pos->color
        for (Map.Entry<Integer, List<Vec2i>> e : this.rastersByColor.entrySet()) {
            for (Vec2i vec : e.getValue()) {
                this.posToColor.put(vec, e.getKey());
            }
        }

        // Map raster->center
        for (Integer color : this.rastersByColor.keySet()) {
            Vec2i vecs = this.rastersByColor.get(color).get(0);
            this.centersByColor.put(color, this.voronoi.getCellPos(vecs.x() / scale, vecs.y() / scale, scale));
        }
    }

    public Map<Integer, List<Vec2i>> getRastersByColor() {
        return rastersByColor;
    }

    public Map<Vec2i, Integer> getPosToColor() {
        return posToColor;
    }

    public Map<Integer, Vec2d> getCentersByColor() {
        return centersByColor;
    }
}
