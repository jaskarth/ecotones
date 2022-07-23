package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.tree.trait.Trait;
import supercoder79.ecotones.world.tree.trait.TraitContainer;
import supercoder79.ecotones.world.tree.trait.TreeTraitRegistry;

public class TreeTraitsCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("treetrait").requires(source ->
                    source.hasPermissionLevel(2));

            builder.then(CommandManager.argument("trait", IdentifierArgumentType.identifier())
                            .suggests((ctx, cb) -> CommandSource.suggestIdentifiers(TreeTraitRegistry.getKeys(), cb))
                            .executes(context -> execute(context.getSource(), context.getArgument("trait", Identifier.class))));

            dispatcher.register(builder);
        });
    }

    private static int execute(ServerCommandSource source, Identifier trait) {
        ChunkGenerator generator = source.getWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            TraitContainer<? extends Trait> container = TreeTraitRegistry.get(trait);
            Trait treeTrait = container.get((EcotonesChunkGenerator) generator, new BlockPos(source.getPosition()));

            source.sendFeedback(Text.literal(container.getName() + " trait: " + treeTrait.name()), false);
        } else {
            source.sendFeedback(Text.literal("This only works on ecotones worlds."), false);
        }
        return 0;
    }
}
