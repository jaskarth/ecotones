package com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import com.jaskarth.ecotones.world.worldgen.structure.gen.layout.building.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Cell with buildings
public class BuildingCell extends FenceableCell {
    private List<Building> buildings = new ArrayList<>();

    public BuildingCell(Cell other) {
        super(other);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {
        for (Building building : this.buildings) {
            building.generate(world, building.getWeldingPoint(), chunkGenerator, random);
        }
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }
}
