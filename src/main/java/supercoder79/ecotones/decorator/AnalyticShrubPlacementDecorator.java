package supercoder79.ecotones.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import supercoder79.ecotones.generation.EcotonesChunkGenerator;
import supercoder79.ecotones.util.DataPos;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//stupid name but i had to make it sound cool :P
public class AnalyticShrubPlacementDecorator extends Decorator<ShrubDecoratorConfig> {
    public AnalyticShrubPlacementDecorator(Function<Dynamic<?>, ? extends ShrubDecoratorConfig> configDeserializer) {
        super(configDeserializer);
    }

    //TODO: use a for loop instead of a stream for more control

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, ShrubDecoratorConfig config, BlockPos pos) {
        //gets data on how many shrubs to place based on the soil drainage.
        //performs an abs function on noise to make it [0, 1].
        //drainage of 1: either too much or too little drainage, 50% of target shrub count
        //drainage of 0: perfect drainage, 150% of target shrub count

        double noise = 0.5; // default for if the chunk generator is not ours
        //get noise at position (this is fairly inaccurate because the pos is at the top left of the chunk and we center it
        if (generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }
        double shrubCountCoefficient = 0.5 + noise; //50% to 150%
        //multiply with target count
        double shrubCountRaw = (config.targetCount * shrubCountCoefficient);
        //height of shrub (this is randomized) and shrub count modifications
        int maxShrubHeight = 1;
        if (noise > 0.7) {
            maxShrubHeight = 3;
            shrubCountRaw *= 1.4;
        } else if (noise > 0.4) {
            maxShrubHeight = 2;
            shrubCountRaw *= 1.2;
        } else if (noise < 0.15) {
            // too low, reduce.
            shrubCountRaw *= 0.5;
        }

        //java is bad
        double finalNoise = noise;
        int finalMaxShrubHeight = maxShrubHeight;

        //cast for final shrub count
        int shrubCount = (int) Math.ceil(shrubCountRaw);
        return IntStream.range(0, shrubCount).mapToObj((ix) -> {
            //randomize x and z
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            //test surrounding blockstates to make sure the area is good
            boolean isLikelyInvalid = false;
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
            // mark as invalid if the base isn't a full 3x3 and if there are too many blocks around the surface.
            // this definitely needs more testing.
            if (solidAround > 2 || solidBase < 8) {
                isLikelyInvalid = true;
            }
            int shrubHeightFinal = finalMaxShrubHeight;

            //modulate height based on height of terrain.
            if (y > 90) {
                shrubHeightFinal--;
            }
            if (y > 150) {
                shrubHeightFinal--;
            }
            //ensure the minimum is 1
            shrubHeightFinal = Math.max(shrubHeightFinal, 1);

            //return data and position
            return new DataPos(x, y, z).setData(finalNoise, shrubHeightFinal, isLikelyInvalid);
        });
    }
}
