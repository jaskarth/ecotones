package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.stream.Stream;

public class LargeRockDecorator extends PlacementModifier {
    public static final Codec<LargeRockDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ChanceDecoratorConfig.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, LargeRockDecorator::new));
    private final ChanceDecoratorConfig config;

    public LargeRockDecorator(ChanceDecoratorConfig config) {
        this.config = config;
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        if (random.nextFloat() < 1.0f / config.chance) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            return Stream.of(new BlockPos(x, y, z));
        }

        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.LARGE_ROCK;
    }
}
