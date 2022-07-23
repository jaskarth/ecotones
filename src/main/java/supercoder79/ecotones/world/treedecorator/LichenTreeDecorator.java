package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class LichenTreeDecorator extends TreeDecorator {
    public static final Codec<LichenTreeDecorator> CODEC = Codec.INT.fieldOf("chance").xmap(LichenTreeDecorator::new, decorator -> decorator.chance).stable().codec();

    private final int chance;

    public LichenTreeDecorator(int chance) {
        this.chance = chance;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return EcotonesTreeDecorators.LICHEN;
    }

    @Override
    public void generate(Generator generator) {
        ObjectArrayList<BlockPos> logPositions = generator.getLogPositions();
        Random random = generator.getRandom();
        TestableWorld world = generator.getWorld();

        for (BlockPos pos : logPositions) {
            if (random.nextInt(this.chance) == 0) {
                // Get random direction
                Direction dir = randomDirection(random);
                if (world.testBlockState(pos.offset(dir), s -> s.isAir())) {
                    // Place lichen towards trunk- so get opposite and then place
                    generator.replace(pos.offset(dir), EcotonesBlocks.LICHEN.getDefaultState().with(VineBlock.FACING_PROPERTIES.get(dir.getOpposite()), true));
                }
            }
        }
    }

    private Direction randomDirection(Random random) {
        return Direction.Type.HORIZONTAL.random(random);
    }
}
