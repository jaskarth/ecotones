package supercoder79.ecotones.world.structure.gen.layout;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.util.Vec2i;
import supercoder79.ecotones.util.noise.voronoi.VoronoiRaster;
import supercoder79.ecotones.world.structure.gen.layout.cell.Cell;
import supercoder79.ecotones.world.structure.gen.layout.cell.GeneralCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public abstract class Layout {
    protected final VoronoiRaster voronoi;
    protected final List<Cell> cells = new ArrayList<>();
    protected final Random random;

    protected Layout(long seed, int x, int z) {
        this.voronoi = new VoronoiRaster(seed, x, z, 48, 24);

        for (Map.Entry<Integer, List<Vec2i>> entry : this.voronoi.getRastersByColor().entrySet()) {
            this.cells.add(new GeneralCell(entry.getValue(), this.voronoi.getCentersByColor().get(entry.getKey()), entry.getKey()));
        }

        ImprovedChunkRandom random = new ImprovedChunkRandom((seed));
        random.setCarverSeed(seed, x, z);

        this.random = random;
    }

    public abstract void generate(ChunkGenerator generator, HeightLimitView height);

    public void generateCells(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {
        for (Cell cell : this.cells) {
            cell.generate(world, structureAccessor, chunkGenerator, random, pos);
        }
    }

    protected final void replaceCell(Cell old, Cell replace) {
        for (int i = 0; i < this.cells.size(); i++) {
            if (this.cells.get(i) == old) {
                this.cells.set(i, replace);
                break;
            }
        }
    }

    @Nullable
    protected final Cell findCellWithVoronoiId(int id) {
        for (Cell cell : this.cells) {
            if (cell.getUniqueId() == id) {
                return cell;
            }
        }

        return null;
    }

    // I don't like this either, but it's a good way to hide the complexity of modifying the list while going through it
    protected final void iterateCells(Consumer<Cell> consumer) {
        for (Cell cell : new ArrayList<>(this.cells)) {
            consumer.accept(cell);
        }
    }

    public List<Cell> getCells() {
        return cells;
    }
}
