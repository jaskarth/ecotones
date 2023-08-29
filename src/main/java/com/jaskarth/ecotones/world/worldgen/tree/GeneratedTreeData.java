package com.jaskarth.ecotones.world.worldgen.tree;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public final class GeneratedTreeData {
    public final List<BlockPos> trunkPositions;
    public final List<BlockPos> leafPositions;

    public GeneratedTreeData(List<BlockPos> trunkPositions, List<BlockPos> leafPositions) {
        this.trunkPositions = trunkPositions;
        this.leafPositions = leafPositions;
    }
}
