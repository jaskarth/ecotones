package supercoder79.ecotones;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import supercoder79.ecotones.blocks.EcotonesBlocks;

public class EcotonesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(),
                EcotonesBlocks.shortGrass);

        ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> FoliageColors.getDefaultColor()),
                EcotonesBlocks.shortGrassItem);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                EcotonesBlocks.shortGrass,
                EcotonesBlocks.wildflowersBlock,
                EcotonesBlocks.reeds);
    }
}
