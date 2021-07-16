package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class BlueberryBushDecorator extends Decorator<ShrubDecoratorConfig> {
    public BlueberryBushDecorator(Codec<ShrubDecoratorConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, ShrubDecoratorConfig config, BlockPos pos) {
        List<BlockPos> positions = new ArrayList<>();

        double soilQuality = 0.5;
        double soilPh = 0;
        ChunkGenerator generator = context.getWorld().toServerWorld().getChunkManager().getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX() + 8, pos.getZ() + 8);
            soilPh = ((EcotonesChunkGenerator)generator).getSoilPhAt(pos.getX() + 8, pos.getZ() + 8);
        }

        double rawCount = config.targetCount;
        rawCount *= qualityToDensity(soilQuality);
        rawCount *= phToDensity(soilPh);

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

    // Desmos: 0.8x^{3}+0.15x+0.65
    private static double qualityToDensity(double q) {
        return (0.8 * q * q * q) + (0.2 * q) + 0.65;
    }

    // Desmos: 0.45\left(4^{-x}+0.5\right)
    private static double phToDensity(double p) {
        return 0.45 * (Math.pow(4, -p) + 0.5);
    }
}
