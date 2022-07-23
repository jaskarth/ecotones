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
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SimpleTreePlacementDecorator extends PlacementModifier {
    public static final Codec<SimpleTreePlacementDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SimpleTreeDecorationData.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, SimpleTreePlacementDecorator::new));

    protected final SimpleTreeDecorationData config;

    public SimpleTreePlacementDecorator(SimpleTreeDecorationData config) {
        this.config = config;
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        List<BlockPos> positions = new ArrayList<>();

        double soilQuality = 0.5; // default for if the chunk generator is not ours
        //get noise at position (this is fairly inaccurate because the pos is at the top left of the chunk and we center it)
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
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
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
            if (y < generator.getSeaLevel()) {
                continue;
            }


            //just go with the position if the ground check can be ignored
            if (config.ignoreGroundCheck) {
                positions.add(new BlockPos(x, y, z));
                continue;
            }

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

            if (!(solidAround > 1 || solidBase < 9)) {
                positions.add(new BlockPos(x, y, z));
            }
        }

        return positions.stream();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.SIMPLE_TREE_DECORATOR;
    }

    // Desmos: x^{3}+0.1x+0.4
    protected double qualityToDensity(double q) {
        return (q * q * q) + (0.1 * q) + 0.4;
    }
}
