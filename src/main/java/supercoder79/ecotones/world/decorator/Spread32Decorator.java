package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.stream.Stream;

public class Spread32Decorator extends PlacementModifier {
    public static final Codec<Spread32Decorator> CODEC = Codec.unit(Spread32Decorator::new);

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        return Stream.of(new BlockPos(pos.getX(), random.nextInt(pos.getY() + 32), pos.getZ()));
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.SPREAD_32;
    }
}
