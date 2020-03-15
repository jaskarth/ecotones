package supercoder79.ecotones;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import supercoder79.ecotones.biome.HumidityLayer1Biomes;
import supercoder79.ecotones.biome.HumidityLayer2Biomes;
import supercoder79.ecotones.biome.SwampBiomes;
import supercoder79.ecotones.biome.special.*;
import supercoder79.ecotones.biome.technical.BeachBiome;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.chunk.WorldGeneratorType;
import supercoder79.ecotones.chunk.WorldType;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.surface.EcotonesSurfaces;

public class Ecotones implements ModInitializer {

	public static WorldGeneratorType WORLDGEN_TYPE;

	private static WorldType<?> loadMeOnClientPls;

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
		HazelGroveBiome.init();
		PinePeaksBiome.init();
		UluruBiome.init();

		loadMeOnClientPls = WorldType.ECOTONES;

		WORLDGEN_TYPE = Registry.register(Registry.CHUNK_GENERATOR_TYPE, new Identifier("simplexterrain", "simplex"), new WorldGeneratorType(false, OverworldChunkGeneratorConfig::new));

		ServerTickCallback.EVENT.register(server -> {
			if (server.getTicks() % 300 == 0) {
				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
					if (player.world.getBiome(new BlockPos(player.getPos())) == BlessedSpringsBiome.INSTANCE) {
						player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, player.getRandom().nextInt(200) + 60, 0, false, false, true));
					}
				}
			}
		});
	}
}
