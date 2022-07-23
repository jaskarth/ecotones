package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.DefaultDataHolder;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.tree.gen.BarrenTreeGenerator;

import java.util.Random;

public class BarrenTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    public BarrenTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        DataHolder data;
        if (generator instanceof DataHolder) {
            data = (DataHolder) generator;
        } else {
            data = DefaultDataHolder.INSTANCE;
        }

        BarrenTreeGenerator.INSTANCE.generate(world, pos, random, data, config);

        return true;
    }
}
