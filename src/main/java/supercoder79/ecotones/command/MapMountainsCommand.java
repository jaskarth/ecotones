package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.world.layers.generation.MountainLayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@DevOnly
public class MapMountainsCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapmountains")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_RGB);

        for (int x = -2048; x < 2048; x++) {
            if (x % 512 == 0) {
                source.sendFeedback(Text.literal(((x + 2048) / 4096.0) * 100 + "%"), false);
            }

            for (int z = -2048; z < 2048; z++) {

                img.setRGB(x + 2048, z + 2048, getColorAtCoords(x, z));
            }
        }

        // save the biome map
        Path p = Paths.get("ecotones_mountains.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
            source.sendFeedback(Text.literal("Mapped mountains!"), false);
        } catch (IOException e) {
            source.sendFeedback(Text.literal("Something went wrong, check the log!"), true);
            e.printStackTrace();
        }

        return 0;
    }

    private static int getColorAtCoords(int x, int z) {
        double mountain = MountainLayer.INSTANCE.mountainNoise.sample((x + MountainLayer.INSTANCE.mountainOffsetX) / 3f, (z + MountainLayer.INSTANCE.mountainOffsetZ) / 3f) * 1.25;
        double mountainRanges = 1 - Math.abs(MountainLayer.INSTANCE.mountainRangesNoise.sample((x - MountainLayer.INSTANCE.mountainOffsetX) / 6f, (z - MountainLayer.INSTANCE.mountainOffsetZ) / 6f));
        // Make mountains spawn less frequently near the spawn
        mountain *= MountainLayer.distFactor(x, z);

        if (mountain > 0.75) {
            return getIntFromColor(50, 50, 50);
        }

        if (mountain > 0.5) {
            return getIntFromColor(150, 150, 150);
        }

        if (mountain < -0.8 + (mountainRanges * 0.2)) {
            return getIntFromColor(50, 50, 170);
        }

        return getIntFromColor(255, 255, 255);
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
