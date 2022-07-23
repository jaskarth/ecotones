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
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.EcotonesData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RosemaryDecorator extends PlacementModifier {
    public static final Codec<RosemaryDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ShrubDecoratorConfig.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, RosemaryDecorator::new));

    private final ShrubDecoratorConfig config;

    public RosemaryDecorator(ShrubDecoratorConfig config) {
        this.config = config;
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        List<BlockPos> positions = new ArrayList<>();

        double soilQuality = 0.5;
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof DataHolder) {
            soilQuality = ((DataHolder)generator).get(EcotonesData.SOIL_DRAINAGE, pos.getX() + 8, pos.getZ() + 8);
        }

        double rawCount = config.targetCount;
        rawCount *= qualityToDensity(soilQuality);

        int count = (int)rawCount;
        double chance = rawCount - count;
        if (random.nextDouble() < chance) {
            count++;
        }

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            positions.add(new BlockPos(x, y, z));
        }

        return positions.stream();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.ROSEMARY;
    }

    // Desmos: 0.8x^{3}+0.15x+0.65
    private static double qualityToDensity(double q) {
        return (0.8 * q * q * q) + (0.2 * q) + 0.65;
    }
}
