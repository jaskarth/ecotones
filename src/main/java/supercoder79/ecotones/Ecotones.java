package supercoder79.ecotones;

import net.fabricmc.api.ModInitializer;
import supercoder79.ecotones.biome.*;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.surface.EcotonesSurfaces;

public class Ecotones implements ModInitializer {

	@Override
	public void onInitialize() {
        EcotonesBlocks.init();
		EcotonesFeatures.init();
		EcotonesSurfaces.init();
		HumidityLayer1Biomes.init();
		HumidityLayer2Biomes.init();
		SwampBiomes.init();
		VolcanicBiome.init();
		HotSpringsBiome.init();
	}
}
