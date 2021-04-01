package supercoder79.ecotones.world.tree.trait;

import net.minecraft.util.math.BlockPos;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.List;

public final class TraitContainer<T extends Trait> {
    private final List<T> traits;
    private final String name;
    private final int salt;

    public TraitContainer(List<T> traits, String name, int salt) {
        this.traits = traits;
        this.name = name;
        this.salt = salt;
    }

    public T get(EcotonesChunkGenerator generator, BlockPos pos) {
        return get(generator, pos.getX() >> 4, pos.getZ() >> 4);
    }

    public T get(EcotonesChunkGenerator generator, int x, int z) {
        return get(generator.getTraits(x, z, this.salt));
    }

    public T get(long traits) {
        return this.traits.get((int) Math.floorMod(traits, this.traits.size()));
    }

    public String getName() {
        return this.name;
    }
}
