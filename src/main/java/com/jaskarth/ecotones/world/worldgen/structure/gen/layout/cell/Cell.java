package com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import com.jaskarth.ecotones.util.Vec2d;
import com.jaskarth.ecotones.util.Vec2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents the in-world contents of a voronoi cell in a layout
public abstract class Cell {
    protected final List<Vec2i> positions;
    protected final Vec2d center;
    protected final int uniqueId;

    protected Cell(List<Vec2i> positions, Vec2d center, int uniqueId) {
        this.positions = positions;
        this.center = center;
        this.uniqueId = uniqueId;
    }

    protected Cell(Cell other) {
        this.positions = new ArrayList<>(other.positions);
        this.center = other.center;
        this.uniqueId = other.uniqueId;
    }

    public abstract void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos);

    public List<Vec2i> getPositions() {
        return positions;
    }

    public Vec2d getCenter() {
        return center;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + uniqueId + "]";
    }
}
