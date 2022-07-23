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

import java.util.stream.Stream;

public class AboveQualityDecorator extends PlacementModifier {
    public static final Codec<AboveQualityDecorator> CODEC = Codec.unit(AboveQualityDecorator::new);

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
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

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.ABOVE_QUALITY;
    }
}
