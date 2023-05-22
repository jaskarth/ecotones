package supercoder79.ecotones.world.gen;

import com.mojang.datafixers.util.Either;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SyntheticRegistryEntryList implements RegistryEntryList<Biome> {
    private final List<RegistryEntry<Biome>> list;

    public SyntheticRegistryEntryList(Stream<RegistryEntry<Biome>> stream) {
        this.list = stream.toList();
    }

    @Override
    public Stream<RegistryEntry<Biome>> stream() {
        return list.stream();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Either<TagKey<Biome>, List<RegistryEntry<Biome>>> getStorage() {
        return Either.right(this.list);
    }

    @Override
    public Optional<RegistryEntry<Biome>> getRandom(Random random) {
        return Optional.of(list.get(random.nextInt(list.size())));
    }

    @Override
    public RegistryEntry<Biome> get(int index) {
        return this.list.get(index);
    }

    @Override
    public boolean contains(RegistryEntry<Biome> entry) {
        return this.list.contains(entry);
    }

    @Override
    public boolean ownerEquals(RegistryEntryOwner<Biome> owner) {
        // Assume owner is ok
        return true;
    }

    @Override
    public Optional<TagKey<Biome>> getTagKey() {
        return Optional.empty();
    }

    @NotNull
    @Override
    public Iterator<RegistryEntry<Biome>> iterator() {
        return list.iterator();
    }
}
