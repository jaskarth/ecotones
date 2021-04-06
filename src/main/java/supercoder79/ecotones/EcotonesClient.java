package supercoder79.ecotones;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import supercoder79.ecotones.api.client.ModelDataRegistry;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.client.ClientPacketHandler;
import supercoder79.ecotones.client.Colors;
import supercoder79.ecotones.client.model.DuckEntityModel;
import supercoder79.ecotones.client.model.EcotonesModelLayers;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.client.particle.MapleLeafParticle;
import supercoder79.ecotones.client.particle.SandParticle;
import supercoder79.ecotones.client.render.DuckEntityRenderer;
import supercoder79.ecotones.entity.EcotonesEntities;
import supercoder79.ecotones.items.EcotonesItems;

@Environment(EnvType.CLIENT)
public final class EcotonesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPacketHandler.init();

        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(),
                EcotonesBlocks.HAZEL_LEAVES);

        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : FoliageColors.getDefaultColor(),
                EcotonesBlocks.WIDE_FERN,
                EcotonesBlocks.SHORT_GRASS,
                EcotonesBlocks.WILDFLOWERS,
                EcotonesBlocks.BLUEBELL,
                EcotonesBlocks.LAVENDER,
                EcotonesBlocks.SMALL_LILAC);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
                EcotonesItems.SHORT_GRASS,
                EcotonesItems.WIDE_FERN,
                EcotonesItems.HAZEL_LEAVES);

        // Maple leaves
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Colors.maple(),
                EcotonesItems.MAPLE_LEAVES);

        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> Colors.maple(pos),
                EcotonesBlocks.MAPLE_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                EcotonesBlocks.SHORT_GRASS,
                EcotonesBlocks.WILDFLOWERS,
                EcotonesBlocks.REEDS,
                EcotonesBlocks.COCONUT,
                EcotonesBlocks.HAZEL_LEAVES,
                EcotonesBlocks.HAZEL_SAPLING,
                EcotonesBlocks.SMALL_SHRUB,
                EcotonesBlocks.SANDY_GRASS,
                EcotonesBlocks.CLOVER,
                EcotonesBlocks.BLUEBELL,
                EcotonesBlocks.WIDE_FERN,
                EcotonesBlocks.SMALL_LILAC,
                EcotonesBlocks.CYAN_ROSE,
                EcotonesBlocks.LICHEN,
                EcotonesBlocks.MOSS,
                EcotonesBlocks.PINECONE,
                EcotonesBlocks.MAPLE_LEAVES,
                EcotonesBlocks.BLUEBERRY_BUSH,
                EcotonesBlocks.SWITCHGRASS,
                EcotonesBlocks.ROSEMARY,
                EcotonesBlocks.LAVENDER,
                EcotonesBlocks.SPRUCE_LEAF_PILE,
                EcotonesBlocks.MARIGOLD,
                EcotonesBlocks.MAPLE_SAPLING);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                EcotonesBlocks.BLUEBERRY_JAM_JAR,
                EcotonesBlocks.MAPLE_SYRUP_JAR);

        ParticleFactoryRegistry.getInstance().register(EcotonesParticles.SAND, SandParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(EcotonesParticles.MAPLE_LEAF, MapleLeafParticle.Factory::new);

        EcotonesModelLayers.init();

        ModelDataRegistry.register(EcotonesModelLayers.DUCK, DuckEntityModel.getTexturedModelData());
        EntityRendererRegistry.INSTANCE.register(EcotonesEntities.DUCK, DuckEntityRenderer::new);
    }
}
