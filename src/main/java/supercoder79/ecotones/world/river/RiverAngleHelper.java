package supercoder79.ecotones.world.river;

import supercoder79.ecotones.world.river.graph.RiverNode;

public final class RiverAngleHelper {
    public static final double PI2 = Math.PI * 2;
    public static final double PI_2 = Math.PI / 2;

    public static double wrapRad(double x) {
        while (x >= PI2) {
            x -= PI2;
        }

        while (x < 0) {
            x += PI2;
        }

        return x;
    }

    public static boolean isUnnaturalMerge(RiverNode base, RiverNode addition) {
        double angle = base.angle();
        double leftBound = wrapRad(angle + PI_2);
        double rightBound = wrapRad(angle - PI_2);

        double additionAngle = addition.angle();

        return additionAngle >= leftBound && additionAngle <= rightBound;
    }
}
