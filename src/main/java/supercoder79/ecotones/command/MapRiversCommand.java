package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.world.layers.system.layer.util.CachingLayerSampler;
import supercoder79.ecotones.world.river.PlateSet;
import supercoder79.ecotones.world.river.RiverInterpolator;
import supercoder79.ecotones.world.river.RiverPlating;
import supercoder79.ecotones.world.river.RiverWorker;
import supercoder79.ecotones.world.river.graph.RiverNode;
import supercoder79.ecotones.world.river.graph.RiverSubgraph;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@DevOnly
public class MapRiversCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("maprivers")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        Entity src = source.getEntity();
        BufferedImage img = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_RGB);
        long seed = source.getWorld().getSeed();
        CachingLayerSampler rivers = RiverPlating.build(seed);
        RiverWorker worker = new RiverWorker(seed);

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Random random = new Random();

        Set<RiverNode> nodes = new HashSet<>();
        Set<PlateSet> seen = new HashSet<>();
        for (int x = -2048; x < 2048; x++) {
            if (x % 512 == 0) {
                source.sendFeedback(Text.literal(((x + 2048) / 4096.0) * 100 + "%"), false);
            }

            for (int z = -2048; z < 2048; z++) {
                mutable.set(x, 0, z);

                BlockPos pos = mutable.add(0, 0, 0);
                PlateSet set = worker.forChunk(new ChunkPos(pos));

                if (!seen.contains(set)) {
                    seen.add(set);
                    for (RiverSubgraph subgraph : set.getGraph().getSubgraphs()) {
                        for (RiverNode node : subgraph.getNodes()) {
                            nodes.add(node);
                        }
                    }
                }

                random.setSeed(set.color);
                int red = random.nextInt(256);
                int green = random.nextInt(256);
                int blue = random.nextInt(256);
                img.setRGB(x + 2048, z + 2048, set == PlateSet.OCEAN_MARKER ? 0 : getIntFromColor(red, green, blue));

//                int sample = RiverInterpolator.sample(seed, mutable.add(src.getBlockX(), 0, src.getBlockZ()), rivers);
//                random.setSeed(sample);
//                int red = random.nextInt(256);
//                int green = random.nextInt(256);
//                int blue = random.nextInt(256);
//
//                img.setRGB(x + 2048, z + 2048, sample == 0 ? 0 : getIntFromColor(red, green, blue));
            }
        }

        for (RiverNode node : nodes) {
            int x = ((int) node.x()) + 2048;
            int z = ((int) node.z()) + 2048;
            int rad = ((int) Math.ceil(node.radius()));

//            for (int x1 = -rad; x1 <= rad; x1++) {
//                for (int z1 = -rad; z1 <= rad; z1++) {
//                    if (x1 * x1 + z1 * z1 <= rad * rad) {
//                        if ((x + x1) > 0 && (x + x1) < 4096 && (z + z1) > 0 && (z + z1) < 4096) {
//                            img.setRGB(x + x1, z + z1, 0xFFFFFF00);
//                        }
//                    }
//                }
//            }

            if (node.getSuccessors().size() > 0) {
                // Only 1 successor
                RiverNode successor = node.getSuccessors().get(0);
                int xNext = ((int) successor.x()) + 2048;
                int zNext = ((int) successor.z()) + 2048;

                for (int i = 0; i < 20; i++) {
                    double progress = (double) i / 20.0;

                    int ax = (int)MathHelper.lerp(progress, x, xNext);
                    int az = (int)MathHelper.lerp(progress, z, zNext);

                    for (int x1 = -rad; x1 <= rad; x1++) {
                        for (int z1 = -rad; z1 <= rad; z1++) {
                            if (x1 * x1 + z1 * z1 <= rad * rad) {
                                if ((ax + x1) > 0 && (ax + x1) < 4096 && (az + z1) > 0 && (az + z1) < 4096) {
                                    img.setRGB(ax + x1, az + z1, 0xFFFFFFFF);
                                }
                            }
                        }
                    }
                }
            }

            for (int x1 = -rad; x1 <= rad; x1++) {
                for (int z1 = -rad; z1 <= rad; z1++) {
                    if (x1 * x1 + z1 * z1 <= rad * rad) {
                        if ((x + x1) > 0 && (x + x1) < 4096 && (z + z1) > 0 && (z + z1) < 4096) {
                            img.setRGB(x + x1, z + z1, 0xFFFFFF00);
                        }
                    }
                }
            }
        }

        // save the biome map
        Path p = Paths.get("ecotones_rivers.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
            source.sendFeedback(Text.literal("Mapped rivers!"), false);
        } catch (IOException e) {
            source.sendFeedback(Text.literal("Something went wrong, check the log!"), true);
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
