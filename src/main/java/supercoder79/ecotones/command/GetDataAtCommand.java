package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.tree.Traits;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;

public class GetDataAtCommand {
    public static void init() {
        CommandRegistry.INSTANCE.register(false, dispatcher -> {
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
            source.sendFeedback(new LiteralText("Soil Drainage: " + chunkGenerator.getSoilDrainageNoise().sample(source.getPosition().x, source.getPosition().z)), false);
            source.sendFeedback(new LiteralText("Soil Rockiness: " + chunkGenerator.getSoilRockinessNoise().sample(source.getPosition().x, source.getPosition().z)), false);
            source.sendFeedback(new LiteralText("Soil Quality: " + chunkGenerator.getSoilQualityAt(source.getPosition().x, source.getPosition().z)), false);
            source.sendFeedback(new LiteralText("Oak Tree Trait: " + Traits.get(Traits.OAK, chunkGenerator.getTraits(((int) source.getPosition().x) >> 4, ((int) source.getPosition().z) >> 4)).name()), false);
        } else {
            source.sendFeedback(new LiteralText("This only works on ecotones worlds."), false);
        }

        return 1;
    }
}
