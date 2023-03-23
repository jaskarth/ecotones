package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TreePlacementDecorator extends PlacementModifier {
    public static final Codec<TreePlacementDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TreeGenerationConfig.DecorationData.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, TreePlacementDecorator::new));
    private final TreeGenerationConfig.DecorationData config;

    public TreePlacementDecorator(TreeGenerationConfig.DecorationData config) {
        this.config = config;
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        double soilQuality = 0.0; // default for if the chunk generator is not ours
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }

        //get the height from minSize to minSize + noiseCoefficient (can be more because of noise map)
        int maxHeight = (int) (config.minSize + Math.max(soilQuality * config.noiseCoefficient, 0));

        int targetCount = (int) config.targetCount;
        if (random.nextDouble() < (config.targetCount - targetCount)) {
            targetCount++;
        }

        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < targetCount; i++) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
//            if (y < generator.getSeaLevel()) {
//                continue;
//            }

            //make trees smaller as the height increases
            int maxFinal = maxHeight;
            if (y > 80) {
                maxFinal = Math.max(maxHeight, maxHeight - ((y - 80) / 15));
            }

            if (config.ignoreGroundCheck) {
                positions.add(new DataPos(x, y, z).setMaxHeight(maxFinal + random.nextInt(4)));
                continue;
            }

            int solidAround = 0;
            int solidBase = 0;
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int z1 = -1; z1 <= 1; z1++) {
                    if (context.getBlockState(new BlockPos(x + x1, y - 1, z + z1)).getMaterial().isSolid()) {
                        solidBase++;
                    }

                    for (int y1 = 0; y1 <= 1; y1++) {
                        if (context.getBlockState(new BlockPos(x + x1, y + y1, z + z1)).getMaterial().isSolid()) {
                            solidAround++;
                        }
                    }
                }
            }

            if (solidAround > 1 || solidBase < 9) {
                continue;
            }

            positions.add(new DataPos(x, y, z).setMaxHeight(maxFinal + random.nextInt(4)));
        }

        return positions.stream();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.TREE_DECORATOR;
    }

    // Desmos: x^{3}+2.75x-1.5
    private double qualityToDensity(double q) {
        return (q * q * q) + (2.75 * q) - 1.5;
    }
}
