package com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import com.jaskarth.ecotones.util.Vec2d;
import com.jaskarth.ecotones.util.Vec2i;

import java.util.List;
import java.util.Random;

// General representation of a cell until it's specialized
public class GeneralCell extends Cell {
    public GeneralCell(List<Vec2i> positions, Vec2d center, int uniqueId) {
        super(positions, center, uniqueId);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {
        throw new RuntimeException("Cell generalized!");
    }
}
