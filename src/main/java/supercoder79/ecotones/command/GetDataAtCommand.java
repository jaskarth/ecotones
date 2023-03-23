package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

public class GetDataAtCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("ecotonesdata").requires(source ->
                    source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);
        });
    }

    private static int execute(ServerCommandSource source) {
        ChunkGenerator generator = source.getWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            EcotonesChunkGenerator chunkGenerator = (EcotonesChunkGenerator) generator;
            double x = source.getPosition().x;
            double z = source.getPosition().z;

            source.sendFeedback(Text.literal("Soil Drainage: " + chunkGenerator.getSoilDrainageNoise().sample(x, z)), false);
            source.sendFeedback(Text.literal("Soil Rockiness: " + chunkGenerator.getSoilRockinessNoise().sample(x, z)), false);
            source.sendFeedback(Text.literal("Soil Quality: " + chunkGenerator.getSoilQualityAt(x, z)), false);
            source.sendFeedback(Text.literal("Soil pH: " + chunkGenerator.getSoilPhAt(x, z)), false);
        } else {
            source.sendFeedback(Text.literal("This only works on ecotones worlds."), false);
        }

        return 1;
    }
}
