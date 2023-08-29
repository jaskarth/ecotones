package com.jaskarth.ecotones.mixin;

import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionOptions.class)
public interface DimensionOptionsAccessor {
    @Accessor
    ChunkGenerator getChunkGenerator();

    @Mutable
    @Accessor
    void setChunkGenerator(ChunkGenerator chunkGenerator);
}
