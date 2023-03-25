package supercoder79.ecotones.world.biome.cave;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import org.jetbrains.annotations.NotNull;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.CaveBiome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WrappedCaveBiome extends CaveBiome {
public static WrappedCaveBiome LUSH;
public static WrappedCaveBiome DRIPSTONE;
public static WrappedCaveBiome DEEP_DARK;

    public static void init() {
        LUSH = new WrappedCaveBiome(BiomeKeys.LUSH_CAVES);
        DRIPSTONE = new WrappedCaveBiome(BiomeKeys.DRIPSTONE_CAVES);
        DEEP_DARK = new WrappedCaveBiome(BiomeKeys.DEEP_DARK);
    }

    private final RegistryKey<Biome> key;

    protected WrappedCaveBiome(RegistryKey<Biome> key) {
        this.key = key;
    }

    @Override
    public List<PlacedFeature> getFeatures() {
        List<PlacedFeature> list = new ArrayList<>();
        for (RegistryEntryList<PlacedFeature> l : Ecotones.REGISTRY.get(this.key).getGenerationSettings().getFeatures()) {
            list.addAll(l.stream().map(RegistryEntry::value)
                    .map(p -> new PlacedFeature(p.feature(),
                            mapPlacements(p)
                            )
                    )
                    .toList());
        }

        return list;
    }

    @NotNull
    private List<PlacementModifier> mapPlacements(PlacedFeature p) {
        List<PlacementModifier> list = new ArrayList<>(p.placementModifiers().stream().filter(m -> !(m instanceof BiomePlacementModifier)).toList());
        list.add(new CavePlacementModifier());

        return list;
    }

    private static class CavePlacementModifier extends PlacementModifier {

        @Override
        public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
            return pos.getY() > 50 ? Stream.empty() : Stream.of(pos);
        }

        @Override
        public PlacementModifierType<?> getType() {
            return null;
        }
    }
}
