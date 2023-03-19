package supercoder79.ecotones.world.structure;

import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.*;
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

import java.util.Map;

public final class EcotonesStructures {

    public static final RegistryEntry<Structure> COTTAGE = register(
            of("cottage"), new CottageStructureFeature(createConfig(tag("cottage"), StructureTerrainAdaptation.BEARD_THIN))
    );

    public static final RegistryEntry<Structure> OUTPOST = register(
            of("outpost"), new OutpostStructure(createConfig(tag("outpost"), StructureTerrainAdaptation.NONE))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_OAK = register(
            of("campfire_oak"), new CampfireStructureFeature(createConfig(tag("campfire_oak"), StructureTerrainAdaptation.BURY), new SingleStateFeatureConfig(Blocks.OAK_LOG.getDefaultState()))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_BIRCH = register(
            of("campfire_birch"), new CampfireStructureFeature(createConfig(tag("campfire_birch"), StructureTerrainAdaptation.BURY), new SingleStateFeatureConfig(Blocks.BIRCH_LOG.getDefaultState()))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_SPRUCE = register(
            of("campfire_spruce"), new CampfireStructureFeature(createConfig(tag("campfire_spruce"), StructureTerrainAdaptation.BURY), new SingleStateFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState()))
    );

    public static final RegistryEntry<Structure> CAMPFIRE_DARK_OAK = register(
            of("campfire_dark_oak"), new CampfireStructureFeature(createConfig(tag("campfire_dark_oak"), StructureTerrainAdaptation.BURY), new SingleStateFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState()))
    );

//    public static final StructureFeature<SingleStateFeatureConfig> CAMPFIRE = new CampfireStructureFeature(SingleStateFeatureConfig.CODEC);
//    public static final StructureFeature<DefaultFeatureConfig> COTTAGE = new CottageStructureFeature(DefaultFeatureConfig.CODEC);
//    public static final StructureFeature<DefaultFeatureConfig> OUTPOST = new OutpostStructure(DefaultFeatureConfig.CODEC);

    public static void init() {
//        Registry.register(Registry.STRUCTURE_FEATURE, Ecotones.id("campfire"), CAMPFIRE);
//        Registry.register(Registry.STRUCTURE_FEATURE, Ecotones.id("cottage"), COTTAGE);
//        Registry.register(Registry.STRUCTURE_FEATURE, Ecotones.id("outpost"), OUTPOST);
//
//        StructureFeatureAccessor.getGenerationStepMap().put(CAMPFIRE, GenerationStep.Feature.SURFACE_STRUCTURES);
//        StructureFeatureAccessor.getGenerationStepMap().put(COTTAGE, GenerationStep.Feature.SURFACE_STRUCTURES);
//        StructureFeatureAccessor.getGenerationStepMap().put(OUTPOST, GenerationStep.Feature.SURFACE_STRUCTURES);

//        FabricStructureBuilder.create(Ecotones.id("campfire"), CAMPFIRE)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(20, 4, 2492472)
//                .register();
//
//        FabricStructureBuilder.create(Ecotones.id("cottage"), COTTAGE)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(24, 4, 32183183)
//                .adjustsSurface()
//                .register();
//
//        FabricStructureBuilder.create(Ecotones.id("outpost"), OUTPOST)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(10, 2, 2492472)
//                .register();
    }

    private static TagKey<Biome> tag(String s) {
        return TagKey.of(Registry.BIOME_KEY, Ecotones.id("has_" + s));
    }

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(Registry.STRUCTURE_KEY, Ecotones.id(id));
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
        Registry.register(BuiltinRegistries.STRUCTURE, key.getValue(), structure);

        return BuiltinRegistries.STRUCTURE.entryOf(key);
    }

    private static RegistryEntryList<Biome> getOrCreateBiomeTag(TagKey<Biome> key) {
        return BuiltinRegistries.BIOME.getOrCreateEntryList(key);
    }
}
