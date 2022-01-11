package supercoder79.ecotones.world.river;

import net.minecraft.util.math.ChunkPos;
import supercoder79.ecotones.world.layers.system.layer.util.CachingLayerSampler;

import java.util.ArrayList;
import java.util.List;

public final class RiverWorker {
    private final long seed;

    private final List<PlateSet> plateSets = new ArrayList<>();
    private final CachingLayerSampler riverSampler;

    public RiverWorker(long seed) {
        this.seed = seed;
        this.riverSampler = RiverPlating.build(seed);
    }

    public PlateSet forChunk(ChunkPos pos) {
        long longpos = pos.toLong();

        for (PlateSet set : this.plateSets) {
            if (set.hasChunk(longpos)) {
                return set;
            }
        }

        PlateSet set;
        try {
            set = RiverPlateGenerator.generate(this.seed, pos, this.riverSampler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("River generation failure", e);
        }

        if (set == PlateSet.OCEAN_MARKER) {
            return PlateSet.OCEAN_MARKER;
        }

        this.plateSets.add(set);

        // Lazy cache
        if (this.plateSets.size() > 16) {
            this.plateSets.remove(0);
        }

        return set;
    }
}
