package supercoder79.ecotones.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.generation.EcotonesChunkGenerator;
import supercoder79.ecotones.util.DataPos;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AnalyticTreePlacementDecorator extends Decorator<TreeGenerationConfig> {
    public AnalyticTreePlacementDecorator(Function<Dynamic<?>, ? extends TreeGenerationConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, TreeGenerationConfig config, BlockPos pos) {
        double noise = 0.0; // default for if the chunk generator is not ours
        if (generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }

        //get the height from minSize to minSize + noiseCoefficient (can be more because of noise map bullshit)
        int maxHeight = (int) (config.minSize + Math.max(noise * config.noiseCoefficient, 0));

        int targetCount = 0;
        if (config.targetCount >= 1) {
            targetCount = (int)config.targetCount;
            //increase in high quality areas to make very thick forests
            if (noise > 0.7) {
                targetCount++;
            }

            //decrease the count based on the soil quality
            if (noise < 0.3) {
                if (random.nextDouble() > noise) {
                    targetCount--;
                }
            }

        } else {
            //blessed randomization function
            if (random.nextDouble() < config.targetCount) {
                targetCount++;
            }
        }

        return IntStream.range(0, targetCount).mapToObj((i) -> {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            return new DataPos(x, y, z).setMaxHeight(maxHeight);
        });
    }
}
