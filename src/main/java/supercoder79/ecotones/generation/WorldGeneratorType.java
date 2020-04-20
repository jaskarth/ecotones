package supercoder79.ecotones.generation;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

import java.util.function.Supplier;

public class WorldGeneratorType extends ChunkGeneratorType<EcotonesChunkGeneratorConfig, EcotonesChunkGenerator> {

    public WorldGeneratorType(boolean buffetScreen, Supplier<EcotonesChunkGeneratorConfig> configSupplier) {
        super(null, buffetScreen, configSupplier);
    }

    public static void init() {
        // NO-OP
    }

    @Override
    public EcotonesChunkGenerator create(IWorld world, BiomeSource biomeSource, EcotonesChunkGeneratorConfig config) {
        return new EcotonesChunkGenerator(world, biomeSource, config);
    }
}