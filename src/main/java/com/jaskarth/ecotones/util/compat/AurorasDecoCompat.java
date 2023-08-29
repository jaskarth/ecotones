package com.jaskarth.ecotones.util.compat;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import com.jaskarth.ecotones.util.CampfireLogHelper;
import com.jaskarth.ecotones.util.deco.BlockAttachment;
import com.jaskarth.ecotones.util.deco.BlockDecorations;
import com.jaskarth.ecotones.util.deco.DecorationCategory;
import com.jaskarth.ecotones.util.deco.DefaultBlockDecoration;
import com.jaskarth.ecotones.util.state.DaffodilProvider;
import com.jaskarth.ecotones.world.worldgen.biome.EcotonesBiomeBuilder;
import com.jaskarth.ecotones.world.data.EcotonesData;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;

import java.util.function.Supplier;

public final class AurorasDecoCompat {
    public static final TagKey<Biome> WAY_SIGN_OAK = TagKey.of(RegistryKeys.BIOME, id("feature/way_sign/oak"));
    public static final TagKey<Biome> WAY_SIGN_BIRCH = TagKey.of(RegistryKeys.BIOME, id("feature/way_sign/birch"));
    public static final TagKey<Biome> WAY_SIGN_SPRUCE = TagKey.of(RegistryKeys.BIOME, id("feature/way_sign/taiga"));
    public static final TagKey<Biome> WAY_SIGN_DESERT = TagKey.of(RegistryKeys.BIOME, id("feature/way_sign/desert"));
    private static boolean aurorasDecoEnabled = false;

    public static void init() {
        aurorasDecoEnabled = true;
    }

    public static void setupAurorasDecoStates() {
        CampfireLogHelper.LOG_TO_STATE.put(Blocks.OAK_LOG, getDeco("stump/oak"));
        CampfireLogHelper.LOG_TO_STATE.put(Blocks.BIRCH_LOG, getDeco("stump/birch"));
        CampfireLogHelper.LOG_TO_STATE.put(Blocks.SPRUCE_LOG, getDeco("stump/spruce"));
        CampfireLogHelper.LOG_TO_STATE.put(Blocks.DARK_OAK_LOG, getDeco("stump/dark_oak"));

        BlockDecorations.register(new DefaultBlockDecoration(getDeco("amethyst_lantern")), BlockAttachment.FLOOR, DecorationCategory.LIGHTS);
        BlockDecorations.register(new DefaultBlockDecoration(getDeco("amethyst_lantern").with(LanternBlock.HANGING, true)), BlockAttachment.CEILING, DecorationCategory.LIGHTS);

        BlockDecorations.register(new DefaultBlockDecoration(getDeco("brazier").with(Properties.LIT, true)), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);
        BlockDecorations.register(new DefaultBlockDecoration(getDeco("sawmill").with(Properties.HORIZONTAL_FACING, Direction.SOUTH)), BlockAttachment.FLOOR, DecorationCategory.TABLES);

        try {
            trySetupLogPiles();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Enum<T> & StringIdentifiable> void trySetupLogPiles() throws Exception {
        EnumProperty<T> prop = (EnumProperty) Class.forName("dev.lambdaurora.aurorasdeco.block.AurorasDecoProperties").getField("PART_TYPE").get(null);
        T doubleprop = (T) Class.forName("dev.lambdaurora.aurorasdeco.block.PartType").getField("DOUBLE").get(null);

        BlockDecorations.register(new DefaultBlockDecoration(getDeco("small_log_pile/oak").with(prop, doubleprop)), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        BlockDecorations.register(new DefaultBlockDecoration(getDeco("small_log_pile/spruce").with(prop, doubleprop)), BlockAttachment.FLOOR, DecorationCategory.TABLES);
    }

    private static BlockState getDeco(String name) {
        return Registries.BLOCK.get(new Identifier("aurorasdeco", name)).getDefaultState();
    }

    public static void applyDaffodils(EcotonesBiomeBuilder biome) {
        if (!aurorasDecoEnabled) {
            return;
        }

        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.EVERY_BLOCK_RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                                new DaffodilProvider())
                                .spreadX(5)
                                .spreadZ(5)
                                .tries(32).build())
                        .decorate(EcotonesDecorators.DATA_FUNCTION.configure(EcotonesData.FLOWER_MOSAIC_ALT)));
    }

    public static boolean isAurorasDecoEnabled() {
        return aurorasDecoEnabled;
    }

    public static Supplier<BlockState> lavender() {
        return () -> Registries.BLOCK.get(id("lavender")).getDefaultState();
    }

    private static Identifier id(String name) {
        return new Identifier("aurorasdeco", name);
    }
}
