package supercoder79.ecotones;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import supercoder79.ecotones.biome.*;
import supercoder79.ecotones.biome.special.*;
import supercoder79.ecotones.biome.technical.BeachBiome;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.surface.EcotonesSurfaces;

public class Ecotones implements ModInitializer {

	@Override
	public void onInitialize() {
        EcotonesBlocks.init();
		EcotonesItems.init();
		EcotonesFeatures.init();
		EcotonesSurfaces.init();

		HumidityLayer1Biomes.init();
		HumidityLayer2Biomes.init();
		SwampBiomes.init();

		BeachBiome.init();

		VolcanicBiome.init();
		SuperVolcanicBiome.init();
		HotSpringsBiome.init();
		BlessedSpringsBiome.init();
		OasisBiome.init();
		ThePitsBiome.init();
		GreenSpiresBiome.init();

		ServerTickCallback.EVENT.register(server -> {
			if (server.getTicks() % 300 == 0) {
				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
					if (player.world.getBiome(player.getBlockPos()) == BlessedSpringsBiome.INSTANCE) {
						player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, player.getRandom().nextInt(200) + 60, 0, false, false, true));
					}
				}
			}
		});
	}
}
