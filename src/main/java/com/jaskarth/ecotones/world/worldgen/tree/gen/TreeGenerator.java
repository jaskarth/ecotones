package com.jaskarth.ecotones.world.worldgen.tree.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import com.jaskarth.ecotones.world.data.DataHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.tree.GeneratedTreeData;

import java.util.Random;

public interface TreeGenerator {
    GeneratedTreeData generate(StructureWorldAccess world, BlockPos pos, Random random, DataHolder data, SimpleTreeFeatureConfig config);
}
