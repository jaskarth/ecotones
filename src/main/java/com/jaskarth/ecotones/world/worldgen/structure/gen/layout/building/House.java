package com.jaskarth.ecotones.world.worldgen.structure.gen.layout.building;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public class House extends Building {
    public House(BlockPos weldingPoint, Direction direction) {
        super(weldingPoint, direction);
    }

    @Override
    public void generate(StructureWorldAccess world, BlockPos pos, ChunkGenerator chunkGenerator, Random random) {
        for (int y = 0; y < 3; y++) {
            setBlockState(world, Blocks.OAK_PLANKS.getDefaultState(), pos.up(y));

            for (int x = 1; x < 4; x++) {
                for (int z = 0; z < 3; z++) {
                    setBlockState(world, Blocks.OAK_PLANKS.getDefaultState(), pos.add(x, y, z));
                }
            }
        }
    }
}
