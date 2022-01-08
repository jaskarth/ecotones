package supercoder79.ecotones.world.layers.system.layer;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import supercoder79.ecotones.world.layers.system.layer.type.InitLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum OceanTemperatureLayer implements InitLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int x, int y) {
      PerlinNoiseSampler perlinNoiseSampler = context.getNoiseSampler();
      double d = perlinNoiseSampler.sample((double)x / 8.0, (double)y / 8.0, 0.0);
      if (d > 0.4) {
         return 44;
      } else if (d > 0.2) {
         return 45;
      } else if (d < -0.4) {
         return 10;
      } else {
         return d < -0.2 ? 46 : 0;
      }
   }
}
