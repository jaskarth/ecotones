package com.jaskarth.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestTranslationKeysCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("testtranslations").requires(source ->
                    source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);
        });
    }

    private static int execute(ServerCommandSource source) {
        Registry<Biome> registry = source.getRegistryManager().get(RegistryKeys.BIOME);

        Set<String> set = new HashSet<>();
        int count = 0;

        Language language = Language.getInstance();
        for (Identifier id : registry.getIds()) {
            if (id.getNamespace().equals("ecotones")) {
                String key = Util.createTranslationKey("biome", id);

                if (!language.hasTranslation(key)) {
                    set.add(key);
                    count++;
                }
            }
        }

        for (Identifier id : Registries.BLOCK.getIds()) {
            if (id.getNamespace().equals("ecotones")) {
                String key = Util.createTranslationKey("block", id);

                if (!language.hasTranslation(key)) {
                    set.add(key);
                    count++;
                }
            }
        }

        for (Identifier id : Registries.ITEM.getIds()) {
            if (id.getNamespace().equals("ecotones")) {
                String key = Util.createTranslationKey("item", id);

                if (!language.hasTranslation(key)) {
                    set.add(key);
                    count++;
                }
            }
        }

        int finalCount = count;
        source.sendFeedback(() -> Text.literal("Found " + finalCount + " missing translation keys."), false);
        if (count == 0) {
            // Congratulating myself :)
            source.sendFeedback(() -> Text.literal("Nice job!"), false);
            return 0;
        }

        try {
            Path path = Paths.get("translations.txt");
            source.sendFeedback(() -> Text.literal("Dumping them to " + path.toAbsolutePath()), false);
            File file = new File(path.toString());
            FileWriter writer = new FileWriter(file);


            for (String s : set.stream().sorted().collect(Collectors.toList())) {
                writer.write("\"" + s + "\": \"\"," + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
