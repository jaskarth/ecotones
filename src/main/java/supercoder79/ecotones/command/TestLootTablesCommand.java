package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTable;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.api.DevOnly;

@DevOnly
public class TestLootTablesCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("testloottables").requires(source ->
                    source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);
        });
    }

    private static int execute(ServerCommandSource source) {
        for (Block block : Registry.BLOCK) {
            Identifier id = Registry.BLOCK.getId(block);

            if (id.getNamespace().equals("ecotones")) {
                if (source.getServer().getLootManager().getTable(block.getLootTableId()) == LootTable.EMPTY) {
                    System.out.println("Block [" + id + "] has no loot table ");
                }
            }
        }

        source.sendFeedback(Text.literal("Dumped blocks without loot tables, check the console"), true);

        return 0;
    }
}
