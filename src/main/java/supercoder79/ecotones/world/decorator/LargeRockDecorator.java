package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

import java.util.Random;
import java.util.stream.Stream;

public class LargeRockDecorator extends Decorator<ChanceDecoratorConfig> {
    public LargeRockDecorator(Codec<ChanceDecoratorConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, ChanceDecoratorConfig config, BlockPos pos) {
        if (random.nextFloat() < 1.0f / config.chance) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            return Stream.of(new BlockPos(x, y, z));
        }
        return Stream.empty();
    }
}
