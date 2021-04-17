package supercoder79.ecotones.world.biome.cave;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import supercoder79.ecotones.api.CaveBiome;
import supercoder79.ecotones.blocks.EcotonesBlocks;

public class LimestoneCaveBiome extends CaveBiome {
    public static LimestoneCaveBiome INSTANCE;

    public static void init() {
        INSTANCE = new LimestoneCaveBiome();
    }

    protected LimestoneCaveBiome() {
        this.addFeature(Feature.ORE.configure(
                new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, EcotonesBlocks.LIMESTONE.getDefaultState(), 33))
                    .rangeOf(64)
                    .spreadHorizontally()
                    .repeat(15));
    }
}
