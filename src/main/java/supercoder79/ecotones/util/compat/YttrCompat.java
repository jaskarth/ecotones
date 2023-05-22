package supercoder79.ecotones.util.compat;

import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.util.deco.BlockAttachment;
import supercoder79.ecotones.util.deco.BlockDecorations;
import supercoder79.ecotones.util.deco.DecorationCategory;
import supercoder79.ecotones.util.deco.DefaultBlockDecoration;
import supercoder79.ecotones.world.gen.BiomeGenData;
import supercoder79.ecotones.world.surface.system.ConfiguredSurfaceBuilder;
import supercoder79.ecotones.world.surface.system.ForwardSurfaceConfig;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;

public class YttrCompat {
    private static Identifier id(String path) {
        return new Identifier("yttr", path);
    }

    private static RegistryKey<Biome> key(String path) {
        return RegistryKey.of(RegistryKeys.BIOME, id(path));
    }

    public static void associateGenData() {
        BiomeGenData.LOOKUP.put(key("wasteland"), new BiomeGenData( 0.2, 0.15, 0.95, 2.4,
                new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new ForwardSurfaceConfig(
                        () -> Registries.BLOCK.get(id("wasteland_dirt")).getDefaultState(),
                        () -> Registries.BLOCK.get(id("wasteland_dirt")).getDefaultState(),
                        () -> Registries.BLOCK.get(id("wasteland_dirt")).getDefaultState()
                ))));

    }

    public static void setupYttrStates() {
        BlockDecorations.register(new DefaultBlockDecoration(getY("crafting/table")), BlockAttachment.FLOOR, DecorationCategory.TABLES);
    }

    private static BlockState getY(String name) {
        return Registries.BLOCK.get(new Identifier("yttr", name)).getDefaultState();
    }

    public static void init() {
        Biome wasteland = Ecotones.REGISTRY.get(id("wasteland"));
        Climate.HOT_VERY_DRY.add(wasteland, 0.3);
        Climate.HOT_DRY.add(wasteland, 0.3);
        Climate.WARM_VERY_DRY.add(wasteland, 0.3);
        BiomeRegistries.registerNoBeachBiome(wasteland);
    }
}
