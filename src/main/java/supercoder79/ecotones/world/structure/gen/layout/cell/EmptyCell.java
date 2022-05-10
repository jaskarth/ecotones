package supercoder79.ecotones.world.structure.gen.layout.cell;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.util.Vec2d;
import supercoder79.ecotones.util.Vec2i;

import java.util.List;
import java.util.Random;

// No-Op cell
public class EmptyCell extends Cell {
    public EmptyCell(Cell other) {
        super(other);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {

    }
}
