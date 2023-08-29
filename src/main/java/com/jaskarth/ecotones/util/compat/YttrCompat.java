package com.jaskarth.ecotones.util.compat;

import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.util.deco.BlockAttachment;
import com.jaskarth.ecotones.util.deco.BlockDecorations;
import com.jaskarth.ecotones.util.deco.DecorationCategory;
import com.jaskarth.ecotones.util.deco.DefaultBlockDecoration;
import com.jaskarth.ecotones.world.worldgen.gen.BiomeGenData;
import com.jaskarth.ecotones.world.worldgen.surface.system.ConfiguredSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.ForwardSurfaceConfig;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;

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
