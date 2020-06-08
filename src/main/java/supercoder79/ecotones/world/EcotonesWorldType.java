package supercoder79.ecotones.world;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.world.generation.EcotonesBiomeSource;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;

public class EcotonesWorldType extends GeneratorType {
    public EcotonesWorldType() {
        super("ecotones");
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator method_29076(long l) {
        return new EcotonesChunkGenerator(new EcotonesBiomeSource(l), l);
    }
}
