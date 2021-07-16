package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import supercoder79.ecotones.api.Climate;

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
        CommandRegistry.INSTANCE.register(false, dispatcher -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("dumpclimates").requires(source ->
                    source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);
        });
    }

    private static int execute(ServerCommandSource source) {
        Path path = Paths.get("climates.txt");
        source.sendFeedback(new LiteralText("Dumping climates to " + path.toAbsolutePath()), false);

        try {
            File file = new File(path.toString());
            FileWriter writer = new FileWriter(file);

            for (Climate climate : EnumSet.allOf(Climate.class)) {
                writer.write("Biomes for climate " + climate + "\n");
                double totalWeight = climate.getTotalWeight();

                for (Climate.Entry entry : climate.getBiomeEntries()) {
                    double weight = entry.getWeight();
                    writer.write("\t" + entry.getBiome().getValue() + " -> " + weight + " (" +FORMAT.format ((weight / totalWeight) * 100) + "%)" + "\n");
                }
            }

            writer.close();
            source.sendFeedback(new LiteralText("Done."), false);
        } catch (IOException e) {
            source.sendFeedback(new LiteralText("Something went wrong, check the log!"), true);
            e.printStackTrace();
        }

        return 1;
    }
}
