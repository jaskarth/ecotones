package supercoder79.ecotones.util;

import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class Shapes {
    // Code used from Terraform
    public static void circle(BlockPos.Mutable origin, double radius, Consumer<BlockPos.Mutable> consumer) {
        int x = origin.getX();
        int z = origin.getZ();
        double radiusSq = radius * radius;
        int radiusCeil = (int) Math.ceil(radius);

        for (int dz = -radiusCeil; dz <= radiusCeil; ++dz) {
            int dzSq = dz * dz;

            for (int dx = -radiusCeil; dx <= radiusCeil; ++dx) {
                int dxSq = dx * dx;
                if ((double) (dzSq + dxSq) <= radiusSq) {
                    origin.set(x + dx, origin.getY(), z + dz);
                    consumer.accept(origin);
                }
            }
        }
    }
}
