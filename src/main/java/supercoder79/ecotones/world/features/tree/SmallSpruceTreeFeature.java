package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.terraformersmc.terraform.util.Shapes;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.tree.SmallSpruceTrait;
import supercoder79.ecotones.tree.Traits;
import supercoder79.ecotones.tree.smallspruce.DefaultSmallSpruceTrait;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmallSpruceTreeFeature extends Feature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);

    public SmallSpruceTreeFeature() {
        super(SimpleTreeFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, SimpleTreeFeatureConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        // Trait data
        SmallSpruceTrait trait = DefaultSmallSpruceTrait.INSTANCE;
        if (generator instanceof EcotonesChunkGenerator) {
            long traits = ((EcotonesChunkGenerator) generator).getTraits(pos.getX() >> 4, pos.getZ() >> 4, TreeType.SMALL_SPRUCE_SALT);
            trait = Traits.get(Traits.SMALL_SPRUCE, traits);
        }

        int heightAddition = trait.extraHeight(random);

        double maxRadius = trait.maxRadius(random);

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int y = 0; y < 8 + heightAddition; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            mutable.move(Direction.UP);
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, 1 + heightAddition);

        List<BlockPos> leaves = new ArrayList<>();
        for (int y = 0; y < 9; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * trait.model(y / 10.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                    leaves.add(leafPos.toImmutable());
                }
            });
            mutable.move(Direction.UP);
        }

        // Generate pinecones
        PINECONES.generate(world, random, ImmutableList.of(), leaves, ImmutableSet.of(), new BlockBox());

        return false;
    }
}
