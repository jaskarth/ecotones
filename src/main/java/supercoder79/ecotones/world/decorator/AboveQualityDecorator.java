package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.Random;
import java.util.stream.Stream;

public class AboveQualityDecorator extends Decorator<NopeDecoratorConfig> {
    public AboveQualityDecorator(Codec<NopeDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, NopeDecoratorConfig config, BlockPos pos) {
        double noise = 0.5; // default for if the chunk generator is not ours
        //get noise at position (this is fairly inaccurate because the pos is at the top left of the chunk and we center it
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            noise = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }

        if (noise > 0.5) {
            if (random.nextDouble() < (noise - 0.5)) {
                int x = random.nextInt(16) + pos.getX();
                int z = random.nextInt(16) + pos.getZ();
                int y = context.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
                return Stream.of(new BlockPos(x, y, z));
            }
        }

        return Stream.empty();
    }
}
