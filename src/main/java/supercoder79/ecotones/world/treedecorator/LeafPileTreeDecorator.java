package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

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
    public void generate(StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box) {
        for (BlockPos pos : leavesPositions) {
            if (random.nextInt(this.chance) == 0) {
                BlockPos localPos = pos.add(random.nextInt(this.offset) - random.nextInt(this.offset), 0, random.nextInt(this.offset) - random.nextInt(this.offset));
                BlockPos topPos = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR_WG, localPos);

                if (topPos.getY() <= pos.getY() && world.getBlockState(topPos).getMaterial().isReplaceable() && world.getBlockState(topPos.down()).isOf(Blocks.GRASS_BLOCK)) {
                    world.setBlockState(topPos, this.state, 3);
                }
            }
        }
    }
}
