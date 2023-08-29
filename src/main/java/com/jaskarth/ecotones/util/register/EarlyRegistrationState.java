package com.jaskarth.ecotones.util.register;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.structure.Structure;

import java.util.HashMap;
import java.util.Map;

public class EarlyRegistrationState {
    public static RegistryWrapper.WrapperLookup globalBuiltins;

    public static final BiMap<Identifier, Structure> STRUCTURES = HashBiMap.create();
    public static final BiMap<Identifier, StructureSet> STRUCTURE_SETS = HashBiMap.create();
}
