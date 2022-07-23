package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.AbstractCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import supercoder79.ecotones.world.data.DataHolder;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataFunctionDecorator extends PlacementModifier {
    public static final Codec<DataFunctionDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(d -> d.id)
    ).apply(instance, DataFunctionDecorator::new));

    private final Identifier id;

    public DataFunctionDecorator(Identifier id) {
        this.id = id;
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {

        int count = 0;

        if (context.getWorld().toServerWorld().getChunkManager().getChunkGenerator() instanceof DataHolder data) {
            count = (int) data.get(this.id).get(pos.getX() / 16.0, pos.getZ() / 16.0);
        }

        return IntStream.range(0, count).mapToObj((i) -> {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = context.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            return new BlockPos(x, y, z);
        });
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.DATA_FUNCTION;
    }
}
