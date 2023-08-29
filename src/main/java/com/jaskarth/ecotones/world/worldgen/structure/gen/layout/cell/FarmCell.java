package com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell;

import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import com.jaskarth.ecotones.util.Vec2i;

import java.util.Random;

public class FarmCell extends FenceableCell {
    public FarmCell(Cell other) {
        super(other);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {
        for (Vec2i cellPos : this.positions) {
            BlockPos p = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(cellPos.x(), 0, cellPos.y()));
            if (world.getBlockState(p.down()).isOf(Blocks.GRASS_BLOCK)) {
                if (random.nextInt(8) != 0) {
                    world.setBlockState(p.down(), Blocks.FARMLAND.getDefaultState().with(Properties.MOISTURE, 7), 3);
                    if (random.nextInt(3) > 0) {
                        world.setBlockState(p, Blocks.WHEAT.getDefaultState().with(Properties.AGE_7, random.nextInt(8)), 3);
                    }
                } else {
                    BlockPos n = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(cellPos.x(), 0, cellPos.y() + 1));
                    BlockPos e = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(cellPos.x() + 1, 0, cellPos.y()));
                    BlockPos s = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(cellPos.x(), 0, cellPos.y() - 1));
                    BlockPos w = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(cellPos.x() - 1, 0, cellPos.y()));

                    if (world.getBlockState(n.down()).isOpaque() && world.getBlockState(e.down()).isOpaque() && world.getBlockState(s.down()).isOpaque() && world.getBlockState(w.down()).isOpaque()) {
                        world.setBlockState(p.down(), Blocks.WATER.getDefaultState(), 3);
                    }
                }
            }
        }
    }
}
