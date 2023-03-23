package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.world.layers.generation.ClimateLayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@DevOnly
public class MapClimatesCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapclimates")
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
        Path p = Paths.get("ecotones_climates.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
            source.sendFeedback(Text.literal("Mapped climates!"), false);
        } catch (IOException e) {
            source.sendFeedback(Text.literal("Something went wrong, check the log!"), true);
            e.printStackTrace();
        }

        return 0;
    }

    private static int getColorAtCoords(int x, int z) {
        double humidity = MathHelper.clamp(ClimateLayer.humidityNoise.sample((x + ClimateLayer.INSTANCE.humidityOffsetX) / 6.0, (z + ClimateLayer.INSTANCE.humidityOffsetZ) / 6.0) * 1.1, -1, 1);
        double temperature = ClimateLayer.temperatureNoise.sample((x + ClimateLayer.INSTANCE.temperatureOffsetX) / 8.0, (z + ClimateLayer.INSTANCE.temperatureOffsetZ) / 8.0);

        if (temperature > 0) {
            // === Hot Biomes ===
            if (humidity > 0.8) {
                return getIntFromColor(0, 255, 0);
            }
            if (humidity > 0.6) {
                return getIntFromColor(0, 225, 0);
            }
            if (humidity > 0.4) {
                return getIntFromColor(0, 190, 0);
            }
            if (humidity > 0.2) {
                return getIntFromColor(30, 160, 0);
            }
            if (humidity > -0.2) {
                return getIntFromColor(60, 130, 0);
            }
            if (humidity > -0.4) {
                return getIntFromColor(90, 100, 0);
            }
            if (humidity > -0.6) {
                return getIntFromColor(120, 70, 0);
            }

            return getIntFromColor(150, 30, 0);
        } else {
            // === Warm Biomes ===
            if (humidity > 0.8) {
                return getIntFromColor(0, 0, 255);
            }
            if (humidity > 0.6) {
                return getIntFromColor(0, 0, 225);
            }
            if (humidity > 0.4) {
                return getIntFromColor(0, 0, 190);
            }
            if (humidity > 0.2) {
                return getIntFromColor(30, 0, 160);
            }
            if (humidity > -0.2) {
                return getIntFromColor(60, 0, 130);
            }
            if (humidity > -0.4) {
                return getIntFromColor(90, 0, 100);
            }
            if (humidity > -0.6) {
                return getIntFromColor(120, 0, 70);
            }

            return getIntFromColor(150, 0, 30);
        }
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
