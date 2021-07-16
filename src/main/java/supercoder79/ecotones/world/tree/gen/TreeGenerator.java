package supercoder79.ecotones.world.tree.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.tree.GeneratedTreeData;

import java.util.Random;

public interface TreeGenerator {
    GeneratedTreeData generate(StructureWorldAccess world, BlockPos pos, Random random, DataHolder data, SimpleTreeFeatureConfig config);
}
