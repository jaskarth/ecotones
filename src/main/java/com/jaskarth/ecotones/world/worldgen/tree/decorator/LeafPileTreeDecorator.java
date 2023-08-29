package com.jaskarth.ecotones.world.worldgen.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

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
    public void generate(Generator generator) {
        ObjectArrayList<BlockPos> leavesPositions = generator.getLeavesPositions();
        Random random = generator.getRandom();
        TestableWorld world = generator.getWorld();

        for (BlockPos pos : leavesPositions) {
            if (random.nextInt(this.chance) == 0) {
                BlockPos localPos = pos.add(random.nextInt(this.offset) - random.nextInt(this.offset), 0, random.nextInt(this.offset) - random.nextInt(this.offset));
                BlockPos topPos = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR_WG, localPos);

                if (topPos.getY() <= pos.getY() && world.testBlockState(topPos, s -> s.isReplaceable()) && world.testBlockState(topPos.down(), s -> s.isOf(Blocks.GRASS_BLOCK))) {
                    generator.replace(topPos, this.state);
                }
            }
        }
    }
}
