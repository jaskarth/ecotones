package supercoder79.ecotones.generation;

import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

public class EcotonesChunkGeneratorConfig extends OverworldChunkGeneratorConfig {
    @Override
    public int getVillageDistance() {
        return 40;
    }

    @Override
    public int getVillageSeparation() {
        return 10;
    }
}
