package supercoder79.ecotones.world.river.phys;

import net.minecraft.util.math.Vec2f;
import supercoder79.ecotones.world.river.graph.RiverNode;

import java.util.ArrayList;
import java.util.List;

public final class AABB {
    private final double minX;
    private final double minZ;
    private final double maxX;
    private final double maxZ;
    private final List<RiverNode> components;

    public AABB(double minX, double minZ, double maxX, double maxZ) {
        this(minX, minZ, maxX, maxZ, new ArrayList<>());
    }

    public AABB(double minX, double minZ, double maxX, double maxZ, List<RiverNode> components) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.components = components;
    }

    public static AABB buildLine(RiverNode n1, RiverNode n2) {
        double minX = Math.min(n1.x() - n1.radius(), n2.x() - n2.radius());
        double minZ = Math.min(n1.z() - n1.radius(), n2.z() - n2.radius());
        double maxX = Math.max(n1.x() + n1.radius(), n2.x() + n2.radius());
        double maxZ = Math.max(n1.z() + n1.radius(), n2.z() + n2.radius());

        return new AABB(minX, minZ, maxX, maxZ, List.of(n1, n2));
    }

    public static AABB merge(AABB a, AABB b) {
        return new AABB(Math.min(a.minX, b.minX), Math.min(a.minZ, b.minZ), Math.max(a.maxX, b.maxX), Math.max(a.maxZ, b.maxZ));
    }

    public boolean clip(AABB aabb) {
        return clip(aabb.minX, aabb.minZ, aabb.maxX, aabb.maxZ);
    }

    public boolean clip(double minX, double minZ, double maxX, double maxZ) {
        return this.maxX >= minX && this.minX <= maxX && this.maxZ >= minZ && this.minZ <= maxZ;
    }

//    public Vec2f findIntersectionBetween(AABB aabb) {
//        if (this.components.size() != 2 || aabb.components.size() != 2) {
//            throw new IllegalStateException("Cannot compute intersection when AABB has " + this.components.size() + " and " + aabb.components.size() + " components");
//        }
//
//        RiverNode n1a = this.components.get(0);
//        RiverNode n1b = this.components.get(0);
//        RiverNode n2a = aabb.components.get(0);
//        RiverNode n2b = aabb.components.get(0);
//
//        double slope1 = (n1a.z() - n1b.z()) / (n1a.x() - n1b.x());
//        double slope2 = (n2a.z() - n2b.z()) / (n2a.x() - n2b.x());
//
//    }

    public double getSizeX() {
        return maxX - minX;
    }

    public double getSizeZ() {
        return maxZ - minZ;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public List<RiverNode> getComponents() {
        return components;
    }

    public String toString() {
        return "AABB[" + this.minX + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxZ + "]";
    }
}
