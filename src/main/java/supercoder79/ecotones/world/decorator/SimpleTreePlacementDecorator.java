package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.Decorator;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class SimpleTreePlacementDecorator extends Decorator<SimpleTreeDecorationData> {
    public SimpleTreePlacementDecorator(Codec<SimpleTreeDecorationData> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldAccess world, ChunkGenerator generator, Random random, SimpleTreeDecorationData config, BlockPos pos) {
        List<BlockPos> positions = new ArrayList<>();

        double soilQuality = 0.5; // default for if the chunk generator is not ours
        //get noise at position (this is fairly inaccurate because the pos is at the top left of the chunk and we center it)
        if (generator instanceof EcotonesChunkGenerator) {
            soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
        }

        //basic amount modulation
        double rawAmt = config.count * qualityToDensity(soilQuality);
        int amt = (int) rawAmt;

        if (random.nextDouble() < (rawAmt - amt)) {
            amt++;
        }

        for (int i = 0; i < amt; i++) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            //just go with the position if the ground check can be ignored
            if (config.ignoreGroundCheck) {
                positions.add(new BlockPos(x, y, z));
                continue;
            }

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

            if (!(solidAround > 1 || solidBase < 9)) {
                positions.add(new BlockPos(x, y, z));
            }
        }

        return positions.stream();
    }

    // Desmos: x^{3}+0.1x+0.4
    protected double qualityToDensity(double q) {
        return (q * q * q) + (0.1 * q) + 0.4;
    }
}
