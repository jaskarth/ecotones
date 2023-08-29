package com.jaskarth.ecotones.world.worldgen.river.phys;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import com.jaskarth.ecotones.world.worldgen.river.graph.RiverNode;

public final class BruteforceIntersect {
    public static Vec2f findBestIntersection(AABB existing, AABB intersect) {
        if (existing.getComponents().size() != 2 || intersect.getComponents().size() != 2) {
            throw new IllegalStateException("Cannot compute intersection when AABB has " + existing.getComponents().size() + " and " + intersect.getComponents().size() + " components");
        }

        BoundedPixelSpace space = new BoundedPixelSpace(AABB.merge(existing, intersect));
        space.initializeWithLine(existing);

        RiverNode first = intersect.getComponents().get(0);
        RiverNode second = intersect.getComponents().get(1);

        // TODO: lerp radius
        int rad = ((int) Math.ceil(first.radius()));

        int x = ((int) first.x());
        int z = ((int) first.z());
        int xNext = ((int) second.x());
        int zNext = ((int) second.z());

        Vec2f best = null;
        int bestAmt = Integer.MIN_VALUE;

        for (int i = 0; i < 20; i++) {
            double progress = (double) i / 20.0;

            int ax = (int) MathHelper.lerp(progress, x, xNext);
            int az = (int) MathHelper.lerp(progress, z, zNext);

            int amt = space.amountIntersecting(ax, az, rad);

            if (amt > bestAmt) {
                bestAmt = amt;
                best = new Vec2f(ax, az);
            }
        }

        return best;
    }
}
