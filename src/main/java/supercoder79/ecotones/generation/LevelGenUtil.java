package supercoder79.ecotones.generation;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.level.LevelGeneratorOptions;
import net.minecraft.world.level.LevelGeneratorType;

public class LevelGenUtil {

    //why is this even necesarry????
    public static LevelGeneratorOptions makeChunkGenerator(LevelGeneratorType levelGeneratorType, Dynamic<?> dynamic) {
        EcotonesChunkGeneratorConfig chunkGeneratorConfig = new EcotonesChunkGeneratorConfig();
        return new LevelGeneratorOptions(levelGeneratorType, dynamic, (world) -> new EcotonesChunkGenerator(world, new EcotonesBiomeSource(new EcotonesBiomeSourceConfig(world.getSeed())), chunkGeneratorConfig));
    }
}
