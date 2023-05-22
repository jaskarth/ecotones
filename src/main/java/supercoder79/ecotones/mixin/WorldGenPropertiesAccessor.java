package supercoder79.ecotones.mixin;

import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ServerPropertiesHandler.WorldGenProperties.class)
public interface WorldGenPropertiesAccessor {
    @Accessor
    static Map<String, RegistryKey<WorldPreset>> getLEVEL_TYPE_TO_PRESET_KEY() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor
    static void setLEVEL_TYPE_TO_PRESET_KEY(Map<String, RegistryKey<WorldPreset>> LEVEL_TYPE_TO_PRESET_KEY) {
        throw new UnsupportedOperationException();
    }
}
