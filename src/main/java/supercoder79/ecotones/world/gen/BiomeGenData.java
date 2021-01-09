package supercoder79.ecotones.world.gen;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public final class BiomeGenData {
    public static final Map<RegistryKey<Biome>, BiomeGenData> LOOKUP = new HashMap<>();
    public static final BiomeGenData DEFAULT = new BiomeGenData(1.0, 1.0);
    public final double volatility;
    public final double hilliness;

    public BiomeGenData(double volatility, double hilliness) {
        this.volatility = volatility;
        this.hilliness = hilliness;
    }
}
