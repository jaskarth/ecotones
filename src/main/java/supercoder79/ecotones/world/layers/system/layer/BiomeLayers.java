package supercoder79.ecotones.world.layers.system.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.world.biome.BiomeKeys;
import supercoder79.ecotones.api.BiomeIdManager;

public class BiomeLayers {

   private static void putCategory(Int2IntOpenHashMap map, Category category, int id) {
      map.put(id, category.ordinal());
   }

   protected static boolean isOcean(int id) {
      return id == BiomeIdManager.getId(BiomeKeys.WARM_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.COLD_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.DEEP_COLD_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.DEEP_FROZEN_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.DEEP_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.DEEP_LUKEWARM_OCEAN);
   }

   protected static boolean isShallowOcean(int id) {
      return id == BiomeIdManager.getId(BiomeKeys.WARM_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.COLD_OCEAN) ||
              id == BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN);
   }

   static enum Category {
      NONE,
      TAIGA,
      EXTREME_HILLS,
      JUNGLE,
      MESA,
      BADLANDS_PLATEAU,
      PLAINS,
      SAVANNA,
      ICY,
      BEACH,
      FOREST,
      OCEAN,
      DESERT,
      RIVER,
      SWAMP,
      MUSHROOM;
   }
}
