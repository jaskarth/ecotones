package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

public class Spread32Decorator extends PlacementModifier {
    public static final Codec<Spread32Decorator> CODEC = Codec.unit(Spread32Decorator::new);

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, BlockPos pos) {
        return Stream.of(new BlockPos(pos.getX(), random.nextInt(pos.getY() + 32), pos.getZ()));
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.SPREAD_32;
    }
}
