package supercoder79.ecotones.client;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public final class Colors {
    public static int maple() {
        // 194, 51, 47
        return 0xc2332f;
    }

    public static int maple(BlockPos pos) {
        double noise = (Biome.FOLIAGE_NOISE.sample(pos.getX() / 80.0, pos.getZ() / 80.0, false) + 1) / 2.0;
        // bottom: 194, 51, 47
        // top: 217, 105, 26
        return combine((int) MathHelper.lerp(noise, 194, 217), (int) MathHelper.lerp(noise, 64, 105), (int) MathHelper.lerp(noise, 38, 26));
    }

    private static int combine(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; // Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; // Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; // Mask out anything not blue.

        return red | green | blue; //Bitwise OR everything together.
    }
}
