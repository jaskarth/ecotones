package supercoder79.ecotones.world.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class AboveQualityDecorator extends Decorator<NopeDecoratorConfig> {
    public AboveQualityDecorator(Function<Dynamic<?>, ? extends NopeDecoratorConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, NopeDecoratorConfig config, BlockPos pos) {
        double noise = 0.5; // default for if the chunk generator is not ours
        //get noise at position (this is fairly inaccurate because the pos is at the top left of the chunk and we center it
        if (generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }

        if (noise > 0.5) {
            if (random.nextDouble() < (noise - 0.5)) {
                int x = random.nextInt(16) + pos.getX();
                int z = random.nextInt(16) + pos.getZ();
                int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
                return Stream.of(new BlockPos(x, y, z));
            }
        }

        return Stream.empty();
    }
}
