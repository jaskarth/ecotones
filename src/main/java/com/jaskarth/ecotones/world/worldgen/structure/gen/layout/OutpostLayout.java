package com.jaskarth.ecotones.world.worldgen.structure.gen.layout;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import com.jaskarth.ecotones.util.Vec2i;
import com.jaskarth.ecotones.util.noise.voronoi.VoronoiHelper;
import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import com.jaskarth.ecotones.world.worldgen.structure.gen.layout.building.House;
import com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell.*;

public class OutpostLayout extends Layout {
    public OutpostLayout(long seed, int x, int z) {
        super(seed, x, z);
    }

    @Override
    public void generate(ChunkGenerator generator, HeightLimitView height) {
        Cell startCell = chooseCentroid();

        // Make center building cell
        BuildingCell building = new BuildingCell(startCell);
        this.replaceCell(startCell, building);

        // Generate buildings
        generateBuildings(building, generator, height);

        // Replace rest of general cells
        iterateCells(cell -> {
            // Replace non-specific cells
            if (cell instanceof GeneralCell) {
                if (this.random.nextInt(4) == 0) {
                    this.replaceCell(cell, new FarmCell(cell));
                } else {
                    this.replaceCell(cell, new EmptyCell(cell));
                }
            }
        });
    }

    private void generateBuildings(BuildingCell building, ChunkGenerator generator, HeightLimitView height) {
        Vec2i center = VoronoiHelper.closestToCenter(building.getPositions(), building.getCenter());

        building.addBuilding(new House(new BlockPos(
                center.x(),
                generator.getHeight(center.x(), center.y(), Heightmap.Type.WORLD_SURFACE_WG, height, null),
                center.y()), FeatureHelper.randomHorizontal(random))
        );

        building.addBuilding(new House(new BlockPos(
                center.x() + 10,
                generator.getHeight(center.x(), center.y(), Heightmap.Type.WORLD_SURFACE_WG, height, null),
                center.y()), FeatureHelper.randomHorizontal(random))
        );

        building.addBuilding(new House(new BlockPos(
                center.x(),
                generator.getHeight(center.x(), center.y(), Heightmap.Type.WORLD_SURFACE_WG, height, null),
                center.y() + 10), FeatureHelper.randomHorizontal(random))
        );
    }
}
