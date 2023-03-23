package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SoilRockinessDecorator extends PlacementModifier {
    public static final Codec<SoilRockinessDecorator> CODEC = Codec.unit(SoilRockinessDecorator::new);

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        // Setup noise
        double noise = 0.5;
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)generator).getSoilRockinessNoise().sample(pos.getX() + 8, pos.getZ() + 8);
        }

        // Convert soil rockiness to density and count
        // Rockiness [0, 1] is converted into density [0, 3.25] in a quadratic fashion.
        double density = rockinessToDensity(noise);
        int count = (int) density;

        // Use the extra count to randomly increase for a gradient
        double extraChance = density - count;
        if (random.nextDouble() < extraChance) {
            count++;
        }

        // Return locations
        if (count == 0) {
            return Stream.empty();
        } else {
            return IntStream.range(0, count).mapToObj((ix) -> {
                int x = random.nextInt(16) + pos.getX();
                int z = random.nextInt(16) + pos.getZ();
                int y = context.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
                return new BlockPos(x, y, z);
            });
        }
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.ROCKINESS;
    }

    // Desmos: x^{2}+2.25x
    private double rockinessToDensity(double r) {
        return (r * r) + (2.25 * r);
    }
}
