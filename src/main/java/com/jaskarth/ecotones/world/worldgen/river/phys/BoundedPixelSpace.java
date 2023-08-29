package com.jaskarth.ecotones.world.worldgen.river.phys;

import net.minecraft.util.math.MathHelper;
import com.jaskarth.ecotones.world.worldgen.river.graph.RiverNode;

import java.util.BitSet;

public final class BoundedPixelSpace {
    private final AABB backing;
    private final BitSet space;
    private final int cornerX;
    private final int cornerZ;
    private final int sizeX;
    private final int sizeZ;

    public BoundedPixelSpace(AABB backing) {
        this.backing = backing;
        this.cornerX = (int) Math.floor(backing.getMinX());
        this.cornerZ = (int) Math.floor(backing.getMinZ());

        // TODO: account for radius in size, instead of using this epic hack
        this.sizeX = expand1(backing.getSizeX()) + 8;
        this.sizeZ = expand1(backing.getSizeZ()) + 8;

        this.space = new BitSet(this.sizeX * this.sizeZ);
    }

    private static int expand1(double x) {
        return x < 0 ? (int) Math.floor(x) : (int) Math.ceil(x);
    }

    public void initializeWithLine(AABB line) {
        RiverNode first = line.getComponents().get(0);
        RiverNode second = line.getComponents().get(1);

        // TODO: lerp radius
        int rad = ((int) Math.ceil(first.radius()));

        int x = ((int) first.x());
        int z = ((int) first.z());
        int xNext = ((int) second.x());
        int zNext = ((int) second.z());

        for (int i = 0; i < 20; i++) {
            double progress = (double) i / 20.0;

            int ax = (int) MathHelper.lerp(progress, x, xNext);
            int az = (int) MathHelper.lerp(progress, z, zNext);

            for (int x1 = -rad; x1 <= rad; x1++) {
                for (int z1 = -rad; z1 <= rad; z1++) {
                    if (x1 * x1 + z1 * z1 <= rad * rad) {
                        this.space.set(this.idx(ax + x1, az + z1));
                    }
                }
            }
        }
    }

    public int amountIntersecting(double x, double z, double radius) {
        int rad = (int) Math.ceil(radius);

        int ax = (int) x;
        int az = (int) z;

        int count = 0;
        for (int x1 = -rad; x1 <= rad; x1++) {
            for (int z1 = -rad; z1 <= rad; z1++) {

                if (x1 * x1 + z1 * z1 <= rad * rad) {
                    int index = this.idx(x1 + ax, z1 + az);

                    // Math busted, too lazy to figure out why
                    if (index >= 0 && index < this.space.size()) {
                        if (this.space.get(index)) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    private int idx(int x, int z) {
        int i = (x - this.cornerX) + ((z - this.cornerZ) * this.sizeX);
        if (i < 0 || i >= this.space.size()) {
            // Debug when it breaks
//            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size " + this.space.size() + " x: " + x + " z: " + z + " cornerX: " + this.cornerX + " cornerZ: " + this.cornerZ + " sizeX: " + this.sizeX + " sizeZ: " + this.sizeZ + " backing: " + this.backing);
        }

        return i;
    }
}
