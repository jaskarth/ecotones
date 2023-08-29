package com.jaskarth.ecotones.world.worldgen.structure;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.structure.Structure;
import com.jaskarth.ecotones.util.register.EarlyRegistrationState;

import java.util.Optional;

public class StructureSetHelpers {
    public static final Codec<RegistryEntry<Structure>> ENTRY_CODEC = RegistryOrCustomElementCodec.of(RegistryKeys.STRUCTURE, Structure.STRUCTURE_CODEC);
    
    public static final Codec<StructureSet.WeightedEntry> WEIGHTED_ENTRY_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ENTRY_CODEC.fieldOf("structure").forGetter(StructureSet.WeightedEntry::structure),
                            Codecs.POSITIVE_INT.fieldOf("weight").forGetter(StructureSet.WeightedEntry::weight)
                    )
                    .apply(instance, StructureSet.WeightedEntry::new)
    );
    
    public static final Codec<StructureSet> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            WEIGHTED_ENTRY_CODEC.listOf().fieldOf("structures").forGetter(StructureSet::structures),
                            StructurePlacement.TYPE_CODEC.fieldOf("placement").forGetter(StructureSet::placement)
                    )
                    .apply(instance, StructureSet::new)
    );

    /**
     * A codec for registry elements. Will prefer to encode/decode objects as
     * identifiers if they exist in a registry and falls back to full encoding/
     * decoding behavior if it cannot do so.
     *
     * <p>The codec's saves and loads {@code Supplier<E>} in order to avoid early
     * loading from registry before a registry is fully loaded from a codec.
     *
     * @param <E> the element type
     * @see net.minecraft.registry.RegistryOps
     */
    public static final class RegistryOrCustomElementCodec<E> implements Codec<RegistryEntry<E>> {
        private final RegistryKey<? extends Registry<E>> registryRef;
        private final Codec<E> elementCodec;
        private final boolean allowInlineDefinitions;

        public static <E> RegistryOrCustomElementCodec<E> of(RegistryKey<? extends Registry<E>> registryRef, Codec<E> elementCodec) {
            return of(registryRef, elementCodec, true);
        }

        public static <E> RegistryOrCustomElementCodec<E> of(RegistryKey<? extends Registry<E>> registryRef, Codec<E> elementCodec, boolean allowInlineDefinitions) {
            return new RegistryOrCustomElementCodec<>(registryRef, elementCodec, allowInlineDefinitions);
        }

        private RegistryOrCustomElementCodec(RegistryKey<? extends Registry<E>> registryRef, Codec<E> elementCodec, boolean allowInlineDefinitions) {
            this.registryRef = registryRef;
            this.elementCodec = elementCodec;
            this.allowInlineDefinitions = allowInlineDefinitions;
        }

        public <T> DataResult<T> encode(RegistryEntry<E> registryEntry, DynamicOps<T> dynamicOps, T object) {
            if (dynamicOps instanceof RegistryOps registryOps) {
                Optional<RegistryEntryOwner<E>> optional = registryOps.getOwner(this.registryRef);
                if (optional.isPresent()) {
                    if (!registryEntry.ownerEquals((RegistryEntryOwner<E>)optional.get())) {
                        return DataResult.error(() -> "Element " + registryEntry + " is not valid in current registry set");
                    }

                    // This is called during data-gen time for structure sets, where they need to encode the structures in the set.
                    // This allows us to fake the fact that we've registered our structures into the builtin registry, so we can do forward references without having to revert
                    // to inline definitions.
                    if (!registryEntry.getKeyOrValue().right().isEmpty()) {
                        if (EarlyRegistrationState.STRUCTURES.containsValue(registryEntry.value())) {
                            return Identifier.CODEC.encode(EarlyRegistrationState.STRUCTURES.inverse().get(registryEntry.value()), dynamicOps, object);
                        }
                    }

                    return registryEntry.getKeyOrValue()
                            .map(key -> Identifier.CODEC.encode(key.getValue(), dynamicOps, object), value -> this.elementCodec.encode((E)value, dynamicOps, object));
                }
            }



            return this.elementCodec.encode(registryEntry.value(), dynamicOps, object);
        }

        @Override
        public <T> DataResult<Pair<RegistryEntry<E>, T>> decode(DynamicOps<T> ops, T input) {
            // Not implemented, shouldn't be called!
            throw new IllegalStateException();
        }

        public String toString() {
            return "RegistryOrCustomElementCodec[" + this.registryRef + " " + this.elementCodec + "]";
        }
    }
}
