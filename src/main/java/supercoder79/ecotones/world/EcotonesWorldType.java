package supercoder79.ecotones.world;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import supercoder79.ecotones.world.gen.EcotonesBiomeSource;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

public class EcotonesWorldType extends GeneratorType {
    public EcotonesWorldType() {
        super("ecotones");
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(DynamicRegistryManager registryManager, long seed) {
        return new EcotonesChunkGenerator(new EcotonesBiomeSource(registryManager.get(Registry.BIOME_KEY), seed), seed);
    }
}
