package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.api.DrainageType;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DrainageSurfaceDecorator extends Decorator<NopeDecoratorConfig> {
    public DrainageSurfaceDecorator(Codec<NopeDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, NopeDecoratorConfig config, BlockPos pos) {
        //get the quality quickly for a heuristic check
        double quality = 1;
        int decorationCount = 0;
        DrainageType type = DrainageType.DEFAULT;
        if (context.generator instanceof EcotonesChunkGenerator) {
            quality = ((EcotonesChunkGenerator)context.generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
            if (quality < 0.2) {
                //if the drainage is poor, let's see what kind of poor drainage - too much or too little?
                double noise = ((EcotonesChunkGenerator)context.generator).getSoilDrainageNoise().sample(pos.getX() + 8, pos.getZ() + 8);
                // too much - place sand
                if (noise > 0.8) {
                    type = DrainageType.TOO_MUCH;
                } else { // too little - clay
                    type = DrainageType.TOO_LITTLE;
                }
                //1 - 6 decorations
                decorationCount = (int) ((0.2 - quality) * 10);
            }
        }

        DrainageType finalType = type;
        return IntStream.range(0, decorationCount).mapToObj((ix) -> {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            boolean isLikelyInvalid = false;
            int solidAround = 0;
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int z1 = -1; z1 <= 1; z1++) {
                    for (int y1 = -1; y1 <= 1; y1++) {
                        if (context.getBlockState(new BlockPos(x + x1, y + y1, z + z1)).getMaterial().isSolid()) {
                            solidAround++;
                        }
                    }
                }
            }
            // if there are too many or too few solid blocks, mark as invalid
            // the result is that most positions are invalid. this is intended.
            if (solidAround > 10 || solidAround < 8) {
                isLikelyInvalid = true;
            }
            return new DataPos(x, y, z).setDrainageType(finalType).setLikelyInvalid(isLikelyInvalid);
        });
    }
}
