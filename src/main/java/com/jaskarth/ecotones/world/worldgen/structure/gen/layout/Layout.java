package com.jaskarth.ecotones.world.worldgen.structure.gen.layout;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import com.jaskarth.ecotones.util.ImprovedChunkRandom;
import com.jaskarth.ecotones.util.Vec2d;
import com.jaskarth.ecotones.util.Vec2i;
import com.jaskarth.ecotones.util.noise.voronoi.VoronoiRaster;
import com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell.Cell;
import com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell.GeneralCell;

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
        this.voronoi = new VoronoiRaster(seed, x, z, 64, 24);

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

    protected Cell chooseCentroid() {
        double x = 0;
        double z = 0;
        for (Cell cell : this.cells) {
            x += cell.getCenter().x();
            z += cell.getCenter().y();
        }

        x /= this.cells.size();
        z /= this.cells.size();

        double dist = 10000;
        Cell closest = null;

        for (Cell cell : this.cells) {
            double d = cell.getCenter().distSqr(new Vec2d(x, z));

            if (d < dist) {
                dist = d;
                closest = cell;
            }
        }

        return closest;
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
