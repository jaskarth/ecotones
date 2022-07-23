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
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ShrubPlacementDecorator extends PlacementModifier {
    public static final Codec<ShrubPlacementDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ShrubDecoratorConfig.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, ShrubPlacementDecorator::new));

    private final ShrubDecoratorConfig config;

    public ShrubPlacementDecorator(ShrubDecoratorConfig config) {
        this.config = config;
    }

    //TODO: use a for loop instead of a stream for more control
    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        //gets data on how many shrubs to place based on the soil drainage.
        //performs an abs function on noise to make it [0, 1].
        //drainage of 0: either too much or too little drainage, 50% of target shrub count
        //drainage of 1: perfect drainage, 150% of target shrub count

        double soilQuality = 0.5; // default for if the chunk generator is not ours
        //get noise at position (this is fairly inaccurate because the pos is at the top left of the chunk and we center it)
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }
        double shrubCountCoefficient = 0.5 + soilQuality; //50% to 150%
        //multiply with target count
        double shrubCountRaw = (config.targetCount * shrubCountCoefficient) * qualityToDensity(soilQuality);
        //height of shrub (this is randomized) and shrub count modifications
        int maxShrubHeight = 1;
        if (soilQuality > 0.7) {
            maxShrubHeight = 3;
        } else if (soilQuality > 0.4) {
            maxShrubHeight = 2;
        }

        //java is bad
        double finalNoise = soilQuality;
        int finalMaxShrubHeight = maxShrubHeight;

        //cast for final shrub count
        int shrubCount = (int) Math.floor(shrubCountRaw);

        if (random.nextDouble() < (shrubCountRaw - shrubCount)) {
            shrubCount++;
        }

        return IntStream.range(0, shrubCount).mapToObj((ix) -> {
            //randomize x and z
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            //test surrounding blockstates to make sure the area is good
            boolean isLikelyInvalid = false;
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

    // Desmos: x^{3}+0.1x+0.4
    private double qualityToDensity(double q) {
        return (q * q * q) + (0.1 * q) + 0.4;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR;
    }
}
