package supercoder79.ecotones.world.structure;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EcotonesStructuresConfig /*extends StructuresConfig*/ {
//    public static Map<RegistryKey<Biome>, List<ConfiguredStructureFeature<?, ?>>> STRUCTURE_DATA = new HashMap<>();
//    private final ImmutableMap<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>>> configuredStructures;
//
//    public EcotonesStructuresConfig(Optional<StrongholdConfig> stronghold, Map<StructureFeature<?>, StructureConfig> structures) {
//        super(stronghold, structures);
//
//        HashMap<StructureFeature<?>, ImmutableMultimap.Builder<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>>> hashMap = new HashMap<>();
//
//        // Vanilla structures (Only relevant are ocean ruins)
//        ConfiguredStructureFeatures.registerAll(
//                (feature, biome) -> (hashMap.computeIfAbsent(feature.feature, featurex -> ImmutableMultimap.builder())).put(feature, biome)
//        );
//
//        // Iterate over Biome<->list
//        for (Map.Entry<RegistryKey<Biome>, List<ConfiguredStructureFeature<?, ?>>> entry : STRUCTURE_DATA.entrySet()) {
//            // Iterate over list->features
//            for (ConfiguredStructureFeature<?, ?> feature : entry.getValue()) {
//                // Map structure to configured structure & biome
//                hashMap.computeIfAbsent(feature.feature, featurex -> ImmutableMultimap.builder()).put(feature, entry.getKey());
//            }
//        }
//
//        this.configuredStructures = hashMap.entrySet()
//                .stream()
//                .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, entry -> (entry.getValue()).build()));
//    }
//
//    @Override
//    public ImmutableMultimap<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>> getConfiguredStructureFeature(StructureFeature<?> feature) {
//        return this.configuredStructures.getOrDefault(feature, ImmutableMultimap.of());
//    }
}
