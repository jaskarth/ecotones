package com.jaskarth.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesChunkGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapSoilPhCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapsoilph")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_RGB);

        EcotonesChunkGenerator chunkGenerator = (EcotonesChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();

        for (int x = -2048; x < 2048; x++) {
            if (x % 512 == 0) {
                source.sendMessage(Text.literal(((x + 2048) / 4096.0) * 100 + "%"));
            }

            for (int z = -2048; z < 2048; z++) {
                int height = (int) ((MathHelper.clamp(chunkGenerator.getSoilPhAt(x, z), -1.0, 1.0) * 127) + 128);

                img.setRGB(x + 2048, z + 2048, getIntFromColor(height, height, height));
            }
        }

        // save the biome map
        Path p = Paths.get("ecotones_soil_ph.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
            source.sendMessage(Text.literal("Mapped soil pH!"));
        } catch (IOException e) {
            source.sendMessage(Text.literal("Something went wrong, check the log!"));
            e.printStackTrace();
        }

        return 0;
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
