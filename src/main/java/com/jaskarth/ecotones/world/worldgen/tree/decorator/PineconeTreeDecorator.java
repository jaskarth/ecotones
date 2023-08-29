package com.jaskarth.ecotones.world.worldgen.tree.decorator;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;

public class PineconeTreeDecorator extends TreeDecorator {
    public static final Codec<PineconeTreeDecorator> CODEC = Codec.INT.fieldOf("rarity").xmap(PineconeTreeDecorator::new, decorator -> decorator.rarity).stable().codec();
    private final int rarity;

    public PineconeTreeDecorator(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public void generate(Generator generator) {
        ObjectArrayList<BlockPos> leavesPositions = generator.getLeavesPositions();
        TestableWorld world = generator.getWorld();
        Random random = generator.getRandom();


        for (BlockPos pos : leavesPositions) {
            if (world.testBlockState(pos.down(), s -> s.isAir())) {
                if (random.nextInt(rarity) == 0) {
                    BlockPos downPos = pos.down();
                    if (world.testBlockState(downPos, s -> s.isAir())) {
                        generator.replace(downPos, EcotonesBlocks.PINECONE.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return EcotonesTreeDecorators.PINECONES;
    }
}
