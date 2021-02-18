package supercoder79.ecotones;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.client.Colors;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.client.particle.MapleLeafParticle;
import supercoder79.ecotones.client.particle.SandParticle;

@Environment(EnvType.CLIENT)
public class EcotonesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(),
                EcotonesBlocks.HAZEL_LEAVES);

        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : FoliageColors.getDefaultColor(),
                EcotonesBlocks.WIDE_FERN,
                EcotonesBlocks.SHORT_GRASS);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
                EcotonesBlocks.SHORT_GRASS_ITEM,
                EcotonesBlocks.WIDE_FERN_ITEM,
                EcotonesBlocks.HAZEL_LEAVES_ITEM);

        // Maple leaves
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Colors.maple(),
                EcotonesBlocks.MAPLE_LEAVES_ITEM);

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
                EcotonesBlocks.BLUEBERRY_BUSH);

        ParticleFactoryRegistry.getInstance().register(EcotonesParticles.SAND, SandParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(EcotonesParticles.MAPLE_LEAF, MapleLeafParticle.Factory::new);
    }
}
