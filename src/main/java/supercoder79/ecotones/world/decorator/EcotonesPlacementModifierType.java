package supercoder79.ecotones.world.decorator;

import com.google.common.reflect.TypeToken;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface EcotonesPlacementModifierType<P extends PlacementModifier> extends PlacementModifierType<P> {
    Class<P> clazz();

    static <P extends PlacementModifier> EcotonesPlacementModifierType<P> make(Class<P> clazz, Codec<P> codec) {
        return new EcotonesPlacementModifierType<>() {
            @Override
            public Class<P> clazz() {
                return clazz;
            }

            @Override
            public Codec<P> codec() {
                return codec;
            }
        };
    }

    // Default decoration config (old nopeconfig)
    default P configure() {
        return configure(null);
    }

    default P configure(Object config) {
        Class<P> clazz = clazz();

        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new IllegalStateException(clazz.getSimpleName() + " needs a public constructor!");
        }

        Constructor<?> constructor = constructors[0];
        if (constructor.getParameterCount() == 1) {
            try {
                return (P) constructor.newInstance(config);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to instantiate " + clazz.getSimpleName() + "!", e);
            }
        } else {
            try {
                return (P) constructor.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to instantiate " + clazz.getSimpleName() + "!", e);
            }
        }
    }
}
