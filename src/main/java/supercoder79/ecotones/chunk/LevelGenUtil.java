package supercoder79.ecotones.chunk;

import com.mojang.datafixers.Dynamic;
import net.minecraft.class_4952;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;

public class LevelGenUtil {

    //why is this even necesarry????
    public static class_4952 makeChunkGenerator(LevelGeneratorType levelGeneratorType, Dynamic<?> dynamic) {
        OverworldChunkGeneratorConfig overworldChunkGeneratorConfig = ChunkGeneratorType.SURFACE.createSettings();
        return new class_4952(levelGeneratorType, dynamic, (world) -> new EcotonesChunkGenerator(world, new EcotonesBiomeSource(new EcotonesBiomeSourceConfig(world.getSeed())), overworldChunkGeneratorConfig));
    }
}
