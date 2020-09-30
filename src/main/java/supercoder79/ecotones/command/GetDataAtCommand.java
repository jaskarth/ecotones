package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.api.TreeType;
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
            double x = source.getPosition().x;
            double z = source.getPosition().z;
            int chunkX = (int) x >> 4;
            int chunkZ = (int) z >> 4;

            source.sendFeedback(new LiteralText("Soil Drainage: " + chunkGenerator.getSoilDrainageNoise().sample(x, z)), false);
            source.sendFeedback(new LiteralText("Soil Rockiness: " + chunkGenerator.getSoilRockinessNoise().sample(x, z)), false);
            source.sendFeedback(new LiteralText("Soil Quality: " + chunkGenerator.getSoilQualityAt(x, z)), false);
            source.sendFeedback(new LiteralText("Oak Tree Trait: " + Traits.get(Traits.OAK, chunkGenerator.getTraits(chunkX, chunkZ, TreeType.OAK_SALT)).name()), false);
            source.sendFeedback(new LiteralText("Small Spruce Tree Trait: " + Traits.get(Traits.SMALL_SPRUCE, chunkGenerator.getTraits(chunkX, chunkZ, TreeType.SMALL_SPRUCE_SALT)).name()), false);
            source.sendFeedback(new LiteralText("Poplar Tree Trait: " + Traits.get(Traits.POPLAR, chunkGenerator.getTraits(chunkX, chunkZ, TreeType.POPLAR_SALT)).name()), false);
        } else {
            source.sendFeedback(new LiteralText("This only works on ecotones worlds."), false);
        }

        return 1;
    }
}
