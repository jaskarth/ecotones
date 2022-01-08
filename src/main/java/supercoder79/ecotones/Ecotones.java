package supercoder79.ecotones;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supercoder79.ecotones.advancement.EcotonesCriteria;
import supercoder79.ecotones.api.ModCompat;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.entity.EcotonesBlockEntities;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.client.sound.EcotonesSounds;
import supercoder79.ecotones.command.*;
import supercoder79.ecotones.entity.EcotonesEntities;
import supercoder79.ecotones.items.EcotonesItemGroups;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.screen.EcotonesScreenHandlers;
import supercoder79.ecotones.util.*;
import supercoder79.ecotones.util.compat.FloralisiaCompat;
import supercoder79.ecotones.util.compat.LambdaFoxesCompat;
import supercoder79.ecotones.util.compat.TerrestriaCompat;
import supercoder79.ecotones.util.compat.TraverseCompat;
import supercoder79.ecotones.util.deco.BlockDecorations;
import supercoder79.ecotones.util.state.EcotonesBlockStateProviders;
import supercoder79.ecotones.util.vein.OreVeins;
import supercoder79.ecotones.world.EcotonesWorldType;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.biome.EcotonesBiomes;
import supercoder79.ecotones.world.data.EcotonesData;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.foliage.EcotonesFoliagePlacers;
import supercoder79.ecotones.world.gen.BiomeGenData;
import supercoder79.ecotones.world.gen.EcotonesBiomeSource;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.structure.EcotonesConfiguredStructures;
import supercoder79.ecotones.world.structure.EcotonesStructurePieces;
import supercoder79.ecotones.world.structure.EcotonesStructures;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;
import supercoder79.ecotones.world.tree.trait.EcotonesTreeTraits;
import supercoder79.ecotones.world.treedecorator.EcotonesTreeDecorators;

public final class Ecotones implements ModInitializer {
	// TODO: split out into it's own class
	public static final Identifier WORLD_TYPE = new Identifier("ecotones", "world_type");

	public static final Logger LOGGER = LogManager.getLogger("ecotones");

	// Dynamic registry
	public static Registry<Biome> REGISTRY;
	private static EcotonesWorldType worldType;
	public static boolean isServerEcotones = false;

	@Override
	public void onInitialize() {
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

		EcotonesDecorators.init();
		OreVeins.init();
		EcotonesFeatures.init();
		EcotonesSurfaces.init();

		EcotonesStructurePieces.init();
		EcotonesStructures.init();
		EcotonesConfiguredStructures.init();

		EcotonesEntities.init();

		// Mod compat handlers that add new blocks that we use
		if (isModLoaded("floralisia")) {
			FloralisiaCompat.init();
			log("Registered Floralisia compat!");
		}

		EcotonesBiomes.init();

		EcotonesData.init();

		EcotonesFuels.init();
		EcotonesComposting.init();

		CampfireLogHelper.initVanilla();
		BlockDecorations.init();

		// Mod Compat handlers
		if (isModLoaded("traverse")) {
			ModCompat.register(TraverseCompat::init);
			log("Registered Traverse compat!");
		}

		if (isModLoaded("terrestria")) {
			ModCompat.register(TerrestriaCompat::init);
			log("Registered Terrestria compat!");
		}

		// Love Aurora's Decorations <3
		if (isModLoaded("aurorasdeco")) {
			ModCompat.register(CampfireLogHelper::initAurorasDeco);
			log("Registered Aurora's Decorations compat!");
		}

		if (isModLoaded("lambdafoxes")) {
			LambdaFoxesCompat.init();
			log("Registered LambdaFoxes compat!");
		}

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
		}

		// Biome count summary and biome finalization
		int ecotonesBiomes = 0;
		for (Identifier id : BuiltinRegistries.BIOME.getIds()) {
			if (id.getNamespace().contains("ecotones")) {
				Biome biome = BuiltinRegistries.BIOME.get(id);
				BiomeGenData data = EcotonesBiomeBuilder.OBJ2DATA.get(biome);

				BiomeGenData.LOOKUP.put(BuiltinRegistries.BIOME.getKey(biome).get(), data);
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
		Registry.register(Registry.BIOME_SOURCE, new Identifier("ecotones", "ecotones"), EcotonesBiomeSource.CODEC);
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier("ecotones", "ecotones"), EcotonesChunkGenerator.CODEC);

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			worldType = new EcotonesWorldType();
		}

		// Store if this server is in ecotones or not
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			if (server.getOverworld().getChunkManager().getChunkGenerator() instanceof EcotonesChunkGenerator) {
				isServerEcotones = true;
			} else {
				isServerEcotones = false;
			}
		});
	}

	public static Identifier id(String name) {
		return new Identifier("ecotones", name);
	}

	public static void log(String str) {
		LOGGER.info("[ecotones] " + str);
	}

	private static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}