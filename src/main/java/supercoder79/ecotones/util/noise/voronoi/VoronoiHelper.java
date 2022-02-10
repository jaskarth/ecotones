package supercoder79.ecotones.util.noise.voronoi;

import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.util.Vec2d;
import supercoder79.ecotones.util.Vec2i;

import java.util.List;

public class VoronoiHelper {
    @Nullable // TODO: return null if center isn't contained within vec2is
    public static Vec2i closestToCenter(List<Vec2i> vecs, Vec2d center) {
        Vec2i closest = null;
        double dist = Double.MAX_VALUE;

        for (Vec2i vec : vecs) {
            double ax = vec.x() - center.x();
            double az = vec.y() - center.y();

            double curDist = ax * ax + az * az;

            if (curDist < dist) {
                dist = curDist;

                closest = vec;
            }
        }

        return closest;
    }
}
