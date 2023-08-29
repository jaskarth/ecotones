package com.jaskarth.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import com.jaskarth.ecotones.api.BiomePicker;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.EnumSet;

public class DumpClimatesCommand {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("dumpclimates").requires(source ->
                    source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);
        });
    }

    private static int execute(ServerCommandSource source) {
        Path path = Paths.get("climates.txt");
        source.sendMessage(Text.literal("Dumping climates to " + path.toAbsolutePath()));

        try {
            File file = new File(path.toString());
            FileWriter writer = new FileWriter(file);

            for (Climate climate : EnumSet.allOf(Climate.class)) {
                writer.write("Biomes for climate " + climate + "\n");
                double totalWeight = climate.pickerFor(ClimateType.REGULAR).getTotalWeight();

                for (BiomePicker.Entry entry : climate.pickerFor(ClimateType.REGULAR).getBiomeEntries()) {
                    double weight = entry.getWeight();
                    writer.write("\t" + entry.getBiome().getValue() + " -> " + weight + " (" +FORMAT.format ((weight / totalWeight) * 100) + "%)" + "\n");
                }
            }

            writer.close();
            source.sendMessage(Text.literal("Done."));
        } catch (IOException e) {
            source.sendMessage(Text.literal("Something went wrong, check the log!"));
            e.printStackTrace();
        }

        return 1;
    }
}
