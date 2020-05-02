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
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.client.particle.SandParticle;

@Environment(EnvType.CLIENT)
public class EcotonesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(),
                EcotonesBlocks.shortGrass,
                EcotonesBlocks.hazelLeavesBlock);

        ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> FoliageColors.getDefaultColor()),
                EcotonesBlocks.shortGrassItem,
                EcotonesBlocks.hazelLeavesItem);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                EcotonesBlocks.shortGrass,
                EcotonesBlocks.wildflowersBlock,
                EcotonesBlocks.reeds,
                EcotonesBlocks.coconutBlock,
                EcotonesBlocks.hazelLeavesBlock,
                EcotonesBlocks.hazelSaplingBlock,
                EcotonesBlocks.smallShrubBlock,
                EcotonesBlocks.sandyGrassBlock,
                EcotonesBlocks.cloverBlock,
                EcotonesBlocks.pineconeBlock);

        ParticleFactoryRegistry.getInstance().register(EcotonesParticles.SAND, SandParticle.Factory::new);
    }
}
