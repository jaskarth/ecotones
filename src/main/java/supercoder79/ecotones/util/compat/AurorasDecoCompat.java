package supercoder79.ecotones.util.compat;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import supercoder79.ecotones.util.CampfireLogHelper;
import supercoder79.ecotones.util.deco.BlockAttachment;
import supercoder79.ecotones.util.deco.BlockDecorations;
import supercoder79.ecotones.util.deco.DecorationCategory;
import supercoder79.ecotones.util.deco.DefaultBlockDecoration;
import supercoder79.ecotones.util.state.DaffodilProvider;
import supercoder79.ecotones.util.state.DeferredBlockStateProvider;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.data.EcotonesData;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.mc.RandomPatchFeatureConfig;

import java.util.Random;
import java.util.function.Supplier;

public final class AurorasDecoCompat {
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
        return Registry.BLOCK.get(new Identifier("aurorasdeco", name)).getDefaultState();
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
        return () -> Registry.BLOCK.get(id("lavender")).getDefaultState();
    }

    private static Identifier id(String name) {
        return new Identifier("aurorasdeco", name);
    }
}
