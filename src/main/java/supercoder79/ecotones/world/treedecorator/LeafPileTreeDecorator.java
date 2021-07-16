package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class LeafPileTreeDecorator extends TreeDecorator {
    public static final Codec<LeafPileTreeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter(c -> c.state),
            Codec.INT.fieldOf("chance").forGetter(c -> c.chance),
            Codec.INT.fieldOf("offset").forGetter(c -> c.offset)
    ).apply(instance, LeafPileTreeDecorator::new));

    private final BlockState state;
    private final int chance;
    private final int offset;

    public LeafPileTreeDecorator(BlockState state, int chance, int offset) {
        this.state = state;
        this.chance = chance;
        this.offset = offset;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return EcotonesTreeDecorators.LEAF_PILE;
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        for (BlockPos pos : leavesPositions) {
            if (random.nextInt(this.chance) == 0) {
                BlockPos localPos = pos.add(random.nextInt(this.offset) - random.nextInt(this.offset), 0, random.nextInt(this.offset) - random.nextInt(this.offset));
                BlockPos topPos = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR_WG, localPos);

                if (topPos.getY() <= pos.getY() && world.testBlockState(topPos, s -> s.getMaterial().isReplaceable()) && world.testBlockState(topPos.down(), s -> s.isOf(Blocks.GRASS_BLOCK))) {
                    replacer.accept(topPos, this.state);
                }
            }
        }
    }
}
