package supercoder79.ecotones;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.biome.HumidityLayer1Biomes;
import supercoder79.ecotones.biome.HumidityLayer2Biomes;
import supercoder79.ecotones.biome.SwampBiomes;
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
	}
}
