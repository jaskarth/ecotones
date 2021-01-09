package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SoilRockinessDecorator extends Decorator<NopeDecoratorConfig> {
    public SoilRockinessDecorator(Codec<NopeDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, NopeDecoratorConfig config, BlockPos pos) {
        // Setup noise
        double noise = 0.5;
        if (context.generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)context.generator).getSoilRockinessNoise().sample(pos.getX() + 8, pos.getZ() + 8);
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

    // Desmos: x^{2}+2.25x
    private double rockinessToDensity(double r) {
        return (r * r) + (2.25 * r);
    }
}
