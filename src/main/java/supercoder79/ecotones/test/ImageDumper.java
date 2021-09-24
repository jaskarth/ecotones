package supercoder79.ecotones.test;


import net.minecraft.util.math.MathHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ImageDumper {
    public static void dumpImage(String fileName, int size, PosFunction function) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        for (int x = -(size / 2); x < size / 2; x++) {
            if (x % Math.max(size / 16, 1) == 0) {
                System.out.println(((x + (size / 2)) / (double)size) * 100 + "%");
            }

            for (int z = -(size / 2); z < size / 2; z++) {
                int color = function.get(x, z);

                img.setRGB(x + size / 2, z + size / 2, color);
            }
        }

        Path p = Paths.get("run", fileName);
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface PosFunction {
        int get(int x, int z);
    }

    public static int getIntFromColor(int red, int green, int blue) {
        red = MathHelper.clamp(red, 0, 255);
        green = MathHelper.clamp(green, 0, 255);
        blue = MathHelper.clamp(blue, 0, 255);

        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
