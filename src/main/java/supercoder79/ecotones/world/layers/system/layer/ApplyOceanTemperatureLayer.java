package supercoder79.ecotones.world.layers.system.layer;

import net.minecraft.world.biome.BiomeKeys;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.gen.EcotonesBiomeLayers;
import supercoder79.ecotones.world.layers.system.layer.type.MergingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public enum ApplyOceanTemperatureLayer implements MergingLayer, IdentityCoordinateTransformer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
      int i = sampler1.sample(this.transformX(x), this.transformZ(z));
      int j = sampler2.sample(this.transformX(x), this.transformZ(z));
      if (!BiomeHelper.isOcean(i)) {
         return i;
      } else {
         for(int m = -8; m <= 8; m += 4) {
            for(int n = -8; n <= 8; n += 4) {
               int o = sampler1.sample(this.transformX(x + m), this.transformZ(z + n));
               if (!BiomeHelper.isOcean(o)) {
                  if (j == BiomeIdManager.getId(BiomeKeys.WARM_OCEAN)) {
                     return BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN);
                  }

                  if (j == BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN)) {
                     return BiomeIdManager.getId(BiomeKeys.COLD_OCEAN);
                  }
               }
            }
         }

         // Deep ocean irrelevant at this stage
//         if (i == 24) {
//            if (j == 45) {
//               return 48;
//            }
//
//            if (j == 0) {
//               return 24;
//            }
//
//            if (j == 46) {
//               return 49;
//            }
//
//            if (j == 10) {
//               return 50;
//            }
//         }

         return j;
      }
   }
}
