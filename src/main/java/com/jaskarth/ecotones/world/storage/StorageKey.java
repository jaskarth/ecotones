package com.jaskarth.ecotones.world.storage;

// Key that stores id, and a de/ser pair to handle nbt serialization
public record StorageKey<T>(String id, Serializer<T> serializer, Deserializer<T> deserializer) {
}
