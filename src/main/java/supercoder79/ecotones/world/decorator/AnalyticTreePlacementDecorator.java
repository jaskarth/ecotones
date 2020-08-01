package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.Decorator;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class AnalyticTreePlacementDecorator extends Decorator<TreeGenerationConfig.DecorationData> {
    public AnalyticTreePlacementDecorator(Codec<TreeGenerationConfig.DecorationData> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldAccess world, ChunkGenerator generator, Random random, TreeGenerationConfig.DecorationData config, BlockPos pos) {
        double soilQuality = 0.0; // default for if the chunk generator is not ours
        if (generator instanceof EcotonesChunkGenerator) {
            soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }

        //get the height from minSize to minSize + noiseCoefficient (can be more because of noise map bullshit)
        int maxHeight = (int) (config.minSize + Math.max(soilQuality * config.noiseCoefficient, 0));

        int targetCount = 0;
        if (config.targetCount >= 1) {
            targetCount = (int)config.targetCount;
            double extraTreesRaw = qualityToDensity(soilQuality);
            int extraTreeCount = (int) Math.floor(extraTreesRaw);

            targetCount += extraTreeCount;

            if (random.nextDouble() < (extraTreesRaw - extraTreeCount)) {
                targetCount++;
            }

        } else {
            //blessed randomization function
            if (random.nextDouble() < config.targetCount) {
                targetCount++;
            }
        }

        List<BlockPos> positions = new ArrayList<>();
        int attempts = 0;
        while (positions.size() < targetCount) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            //make trees smaller as the height increases
            int maxFinal = maxHeight;
            if (y > 80) {
                maxFinal = Math.max(maxHeight, maxHeight - ((y - 80) / 15));
            }

            int solidAround = 0;
            int solidBase = 0;
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int z1 = -1; z1 <= 1; z1++) {
                    if (world.getBlockState(new BlockPos(x + x1, y - 1, z + z1)).getMaterial().isSolid()) {
                        solidBase++;
                    }

                    for (int y1 = 0; y1 <= 1; y1++) {
                        if (world.getBlockState(new BlockPos(x + x1, y + y1, z + z1)).getMaterial().isSolid()) {
                            solidAround++;
                        }
                    }
                }
            }

            if (solidAround > 1 || solidBase < 9) {
                attempts++;
                //give up if we have attempted too many times
                if (attempts > 20) {
                    attempts = 0;
                    positions.add(new DataPos(x, y, z).setMaxHeight(maxFinal).setLikelyInvalid(true));
                } else {
                    continue;
                }
            }
            attempts = 0;

            positions.add(new DataPos(x, y, z).setMaxHeight(maxFinal + random.nextInt(3)));
        }

        return positions.stream();
    }

    // Desmos: x^{3}+2.75x-1.5
    private double qualityToDensity(double q) {
        return (q * q * q) + (2.75 * q) - 1.5;
    }
}
