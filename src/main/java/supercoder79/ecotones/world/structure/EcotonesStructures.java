package supercoder79.ecotones.world.structure;

import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.mixin.StructureFeatureAccessor;
import supercoder79.ecotones.util.register.EarlyRegistrationState;
import supercoder79.ecotones.world.gen.SyntheticRegistryEntryList;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class EcotonesStructures {

    public static final RegistryEntry<Structure> COTTAGE = register(
            of("cottage"), new CottageStructureFeature(createConfig(tag("cottage"), StructureTerrainAdaptation.BEARD_THIN))
    );

    public static final RegistryEntry<Structure> OUTPOST = register(
            of("outpost"), new OutpostStructure(createConfig(tag("outpost"), StructureTerrainAdaptation.NONE))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_OAK = register(
            of("campfire_oak"), new CampfireStructureFeature(createConfig(tag("campfire_oak"), StructureTerrainAdaptation.NONE), new SingleStateFeatureConfig(Blocks.OAK_LOG.getDefaultState()))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_BIRCH = register(
            of("campfire_birch"), new CampfireStructureFeature(createConfig(tag("campfire_birch"), StructureTerrainAdaptation.NONE), new SingleStateFeatureConfig(Blocks.BIRCH_LOG.getDefaultState()))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_SPRUCE = register(
            of("campfire_spruce"), new CampfireStructureFeature(createConfig(tag("campfire_spruce"), StructureTerrainAdaptation.NONE), new SingleStateFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState()))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_DARK_OAK = register(
            of("campfire_dark_oak"), new CampfireStructureFeature(createConfig(tag("campfire_dark_oak"), StructureTerrainAdaptation.NONE), new SingleStateFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState()))
    );

//    public static final StructureFeature<SingleStateFeatureConfig> CAMPFIRE = new CampfireStructureFeature(SingleStateFeatureConfig.CODEC);
//    public static final StructureFeature<DefaultFeatureConfig> COTTAGE = new CottageStructureFeature(DefaultFeatureConfig.CODEC);
//    public static final StructureFeature<DefaultFeatureConfig> OUTPOST = new OutpostStructure(DefaultFeatureConfig.CODEC);

    public static void init() {
    }

    private static TagKey<Biome> tag(String s) {
        return TagKey.of(RegistryKeys.BIOME, Ecotones.id("has_" + s));
    }

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Ecotones.id(id));
    }

    private static Structure.Config createConfig(
            TagKey<Biome> biomeTag, Map<SpawnGroup, StructureSpawns> spawns, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation
    ) {
        return new Structure.Config(getOrCreateBiomeTag(biomeTag), spawns, featureStep, terrainAdaptation);
    }

    private static Structure.Config createConfig(TagKey<Biome> biomeTag, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomeTag, Map.of(), featureStep, terrainAdaptation);
    }

    private static Structure.Config createConfig(TagKey<Biome> biomeTag, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomeTag, Map.of(), GenerationStep.Feature.SURFACE_STRUCTURES, terrainAdaptation);
    }

    private static RegistryEntry<Structure> register(RegistryKey<Structure> key, Structure structure) {
//        Registry.register(BuiltinRegistries.STRUCTURE, key.getValue(), structure);

//        return BuiltinRegistries.STRUCTURE.entryOf(key);

        EarlyRegistrationState.STRUCTURES.put(key.getValue(), structure);

        return RegistryEntry.of(structure);
    }

    private static RegistryEntryList<Biome> getOrCreateBiomeTag(TagKey<Biome> key) {
        System.out.println(EarlyRegistrationState.globalBuiltins.createRegistryLookup().getOrThrow(RegistryKeys.BIOME));
        Optional<RegistryEntryList.Named<Biome>> optional = EarlyRegistrationState.globalBuiltins.createRegistryLookup().getOrThrow(RegistryKeys.BIOME).getOptional(key);
        System.out.println(">>>> " + optional);
//        return BuiltinRegistries.BIOME.getOrCreateEntryList(key);
//        return new SyntheticRegistryEntryList(Stream.of());

        return RegistryEntryList.of(new RegistryEntryOwner<Biome>() {
            @Override
            public boolean ownerEquals(RegistryEntryOwner<Biome> other) {
                return true;
            }
        }, key);
    }
}
