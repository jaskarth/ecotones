package supercoder79.ecotones;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supercoder79.ecotones.advancement.EcotonesCriteria;
import supercoder79.ecotones.api.ModCompat;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.client.particle.EcotonesParticles;
import supercoder79.ecotones.client.sound.EcotonesSounds;
import supercoder79.ecotones.command.*;
import supercoder79.ecotones.compat.TerrestriaCompat;
import supercoder79.ecotones.compat.TraverseCompat;
import supercoder79.ecotones.entity.EcotonesEntities;
import supercoder79.ecotones.items.EcotonesItemGroups;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.util.AiLog;
import supercoder79.ecotones.util.CampfireLogHelper;
import supercoder79.ecotones.util.EcotonesBlockPlacers;
import supercoder79.ecotones.util.deco.BlockDecorations;
import supercoder79.ecotones.world.EcotonesWorldType;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.biome.EcotonesBiomes;
import supercoder79.ecotones.world.biome.special.BlessedSpringsBiome;
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

	@Override
	public void onInitialize() {
		EcotonesCriteria.init();
		EcotonesSounds.init();

		EcotonesParticles.init();
		EcotonesBlockPlacers.init();
		EcotonesFoliagePlacers.init();
		EcotonesTreeDecorators.init();

		EcotonesTreeTraits.init();

		EcotonesItemGroups.init();

        EcotonesBlocks.init();
		EcotonesItems.init();
		EcotonesDecorators.init();
		EcotonesFeatures.init();
		EcotonesSurfaces.init();

		EcotonesStructurePieces.init();
		EcotonesStructures.init();
		EcotonesConfiguredStructures.init();

		EcotonesEntities.init();

		EcotonesBiomes.init();

		EcotonesData.init();

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

		AiLog.init();
		AiLog.log("[System] Starting AI log");

		GetDataAtCommand.init();
		TreeTraitsCommand.init();

		// Dev only commands
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			MapSoilQualityCommand.init();
			MapSoilPhCommand.init();
			DumpClimatesCommand.init();
		}

		int ecotonesBiomes = 0;
		for (Identifier id : BuiltinRegistries.BIOME.getIds()) {
			if (id.getNamespace().contains("ecotones")) {
				Biome biome = BuiltinRegistries.BIOME.get(id);
				BiomeGenData data = EcotonesBiomeBuilder.OBJ2DATA.get(biome);

				BiomeGenData.LOOKUP.put(BuiltinRegistries.BIOME.getKey(biome).get(), data);

				ecotonesBiomes++;
			}
		}

		log("Registering " + ecotonesBiomes + " ecotones biomes!");

		Registry.register(Registry.BIOME_SOURCE, new Identifier("ecotones", "ecotones"), EcotonesBiomeSource.CODEC);
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier("ecotones", "ecotones"), EcotonesChunkGenerator.CODEC);

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			worldType = new EcotonesWorldType();
		}

		// TODO: fix this
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
