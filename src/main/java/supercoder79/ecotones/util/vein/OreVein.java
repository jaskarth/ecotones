package supercoder79.ecotones.util.vein;

import net.minecraft.block.BlockState;

public final class OreVein {
    private final int minY;
    private final int maxY;
    private final int ySpread;
    private final BlockState mainState;
    private final BlockState outerState;
    private final BlockState rareState;
    private final Point point;

    public OreVein(int minY, int maxY, int ySpread, BlockState mainState, BlockState outerState, BlockState rareState, Point point) {
        this.minY = minY;
        this.maxY = maxY;
        this.ySpread = ySpread;
        this.mainState = mainState;
        this.outerState = outerState;
        this.rareState = rareState;
        this.point = point;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getySpread() {
        return ySpread;
    }

    public BlockState getMainState() {
        return mainState;
    }

    public BlockState getOuterState() {
        return outerState;
    }

    public BlockState getRareState() {
        return rareState;
    }

    public Point getPoint() {
        return point;
    }

    public static class Point {
        private final double x;
        private final double z;

        public Point(double x, double z) {
            this.x = x;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getZ() {
            return z;
        }

        public double dist(double x, double z) {
            return (this.x - x) * (this.x - x) + (this.z - z) * (this.z - z);
        }
    }
}
