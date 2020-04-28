package supercoder79.ecotones.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.generation.EcotonesChunkGenerator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SoilRockinessDecorator extends Decorator<NopeDecoratorConfig> {
    public SoilRockinessDecorator(Function<Dynamic<?>, ? extends NopeDecoratorConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, NopeDecoratorConfig config, BlockPos pos) {
        int count = 0;
        double noise = 0.5;
        if (generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)generator).getSoilRockinessNoise().sample(pos.getX() + 8, pos.getZ() + 8) * 2;
        }

        if (noise > 1) {
            noise -= 1;
            count++;
        }
        if (random.nextDouble() < noise) {
            count++;
        }


        if (count == 0) {
            return Stream.empty();
        } else {
            return IntStream.range(0, count).mapToObj((ix) -> {
                int x = random.nextInt(16) + pos.getX();
                int z = random.nextInt(16) + pos.getZ();
                int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
                return new BlockPos(x, y, z);
            });
        }
    }
}
