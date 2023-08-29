package com.jaskarth.ecotones;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.jaskarth.ecotones.world.advancement.EcotonesCriteria;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.blocks.entity.EcotonesBlockEntities;
import com.jaskarth.ecotones.client.particle.EcotonesParticles;
import com.jaskarth.ecotones.client.sound.EcotonesSounds;
import com.jaskarth.ecotones.command.*;
import com.jaskarth.ecotones.config.ConfigReader;
import com.jaskarth.ecotones.config.ConfigSpec;
import com.jaskarth.ecotones.world.entity.EcotonesEntities;
import com.jaskarth.ecotones.datagen.DataGen;
import com.jaskarth.ecotones.world.items.EcotonesItemGroups;
import com.jaskarth.ecotones.world.items.EcotonesItems;
import com.jaskarth.ecotones.recipe.EcotonesRecipes;
import com.jaskarth.ecotones.screen.EcotonesScreenHandlers;
import com.jaskarth.ecotones.util.*;
import com.jaskarth.ecotones.util.compat.*;
import com.jaskarth.ecotones.util.deco.BlockDecorations;
import com.jaskarth.ecotones.util.register.EarlyRegistrationState;
import com.jaskarth.ecotones.util.state.EcotonesBlockStateProviders;
import com.jaskarth.ecotones.util.vein.OreVeins;
import com.jaskarth.ecotones.world.worldgen.biome.EarlyBiomeRegistry;
import com.jaskarth.ecotones.world.worldgen.biome.EcotonesBiomeBuilder;
import com.jaskarth.ecotones.world.worldgen.biome.EcotonesBiomes;
import com.jaskarth.ecotones.world.data.EcotonesData;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.foliage.EcotonesFoliagePlacers;
import com.jaskarth.ecotones.world.worldgen.gen.BiomeGenData;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesBiomeSource;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesChunkGenerator;
import com.jaskarth.ecotones.world.worldgen.structure.*;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;
import com.jaskarth.ecotones.world.worldgen.tree.trait.EcotonesTreeTraits;
import com.jaskarth.ecotones.world.worldgen.tree.decorator.EcotonesTreeDecorators;

public final class Ecotones implements ModInitializer {
	public static final ConfigSpec CONFIG = ConfigReader.read();
	private static final boolean RUN_DATA_GEN = "true".equals(System.getProperty("ECOTONES_RUN_DATAGEN", null));

	public static final Identifier WORLD_TYPE = new Identifier("ecotones", "world_type");

	public static final Logger LOGGER = LogManager.getLogger("ecotones");

	// Dynamic registry
	public static Registry<Biome> REGISTRY;
	public static boolean isServerEcotones = false;

	@Override
	public void onInitialize() {
		long start = System.currentTimeMillis();
		EarlyRegistrationState.globalBuiltins = BuiltinRegistries.createWrapperLookup();

		EcotonesCriteria.init();
		EcotonesSounds.init();

		EcotonesParticles.init();
		EcotonesBlockStateProviders.init();
		EcotonesFoliagePlacers.init();
		EcotonesTreeDecorators.init();

		EcotonesTreeTraits.init();

		EcotonesItemGroups.init();
		EcotonesScreenHandlers.init();

		EcotonesBlocks.init();
		EcotonesBlockEntities.init();
		EcotonesItems.init();

		EcotonesRecipes.init();

		EcotonesDecorators.init();
		OreVeins.init();
		EcotonesFeatures.init();
		EcotonesSurfaces.init();

		EcotonesStructurePieces.init();
		EcotonesStructureTypes.init();
		EcotonesStructures.init();
		EcotonesStructureSets.init();

		EcotonesEntities.init();

		ModCompat.initEarly();

		EcotonesBiomes.init();

		EcotonesData.init();

		EcotonesFuels.init();
		EcotonesComposting.init();

		CampfireLogHelper.initVanilla();
		BlockDecorations.init();

		if (RUN_DATA_GEN) {
			DataGen.run();
		}

		ModCompat.initLate();

		AiLog.init();
		AiLog.log("[System] Starting AI log");

		GetDataAtCommand.init();
		TreeTraitsCommand.init();

		// Dev only commands
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			MapSoilQualityCommand.init();
			MapSoilPhCommand.init();
			DumpClimatesCommand.init();
			TestTranslationKeysCommand.init();
			MapClimatesCommand.init();
			MapMountainsCommand.init();
			MapBiomeColorsCommand.init();
			TestLootTablesCommand.init();
			MapRiversCommand.init();
		}

		// Biome count summary and biome finalization
		int ecotonesBiomes = 0;
		BiomeRegistries.finalizeValues();
		for (Identifier id : EarlyBiomeRegistry.ids()) {
			if (id.getNamespace().contains("ecotones")) {
				Biome biome = EarlyBiomeRegistry.get(id);
				BiomeGenData data = EcotonesBiomeBuilder.OBJ2DATA.get(biome);

				RegistryKey<Biome> key = EarlyBiomeRegistry.get(biome).get();
				BiomeGenData.LOOKUP.put(key, data);
				if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
					BiomeChecker.check(biome);
				}

				ecotonesBiomes++;
			}
		}

		VanillaBiomeData.init();

		log("Registering " + ecotonesBiomes + " ecotones biomes!");
		RegistryReport.report(ecotonesBiomes);

		// register chunk generator and world type
		Registry.register(Registries.BIOME_SOURCE, new Identifier("ecotones", "ecotones"), EcotonesBiomeSource.CODEC);
		Registry.register(Registries.CHUNK_GENERATOR, new Identifier("ecotones", "ecotones"), EcotonesChunkGenerator.CODEC);

		log("Ecotones init took " + (System.currentTimeMillis() - start) + "ms!");
	}

	public static Identifier id(String name) {
		return new Identifier("ecotones", name);
	}

	public static void log(String str) {
		LOGGER.info("[ecotones] " + str);
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}