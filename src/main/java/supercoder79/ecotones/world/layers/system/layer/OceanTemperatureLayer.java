package supercoder79.ecotones.world.layers.system.layer;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.BiomeKeys;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.world.layers.system.layer.type.InitLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum OceanTemperatureLayer implements InitLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int x, int y) {
      PerlinNoiseSampler perlinNoiseSampler = context.getNoiseSampler();
      double d = perlinNoiseSampler.sample((double)x / 8.0, (double)y / 8.0, 0.0);
      if (d > 0.4) {
         return BiomeIdManager.getId(BiomeKeys.WARM_OCEAN);
      } else if (d > 0.2) {
         return BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN);
      } else if (d < -0.4) {
         return BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN);
      } else {
         return d < -0.2 ? BiomeIdManager.getId(BiomeKeys.COLD_OCEAN) : BiomeIdManager.getId(BiomeKeys.OCEAN);
      }
   }
}
