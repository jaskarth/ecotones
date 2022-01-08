package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

public class DuckNestDecorator extends PlacementModifier {
    public static final Codec<DuckNestDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ShrubDecoratorConfig.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, DuckNestDecorator::new));

    private final ShrubDecoratorConfig config;

    public DuckNestDecorator(ShrubDecoratorConfig config) {
        this.config = config;
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, BlockPos pos) {
        double chance = config.targetCount;

        if (random.nextDouble() < chance) {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
            return Stream.of(new BlockPos(x, y, z));
        } else {
            return Stream.empty();
        }
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.DUCK_NEST;
    }
}
