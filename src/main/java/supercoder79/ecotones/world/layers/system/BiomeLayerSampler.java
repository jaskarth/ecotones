package supercoder79.ecotones.world.layers.system;

import net.minecraft.registry.Registry;
import net.minecraft.util.Util;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.world.layers.system.layer.util.CachingLayerSampler;
import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;

public class BiomeLayerSampler {
   private static final Logger LOGGER = LogManager.getLogger();
   private final CachingLayerSampler sampler;

   public BiomeLayerSampler(LayerFactory<CachingLayerSampler> layerFactory) {
      this.sampler = layerFactory.make();
   }

   public RegistryKey<Biome> sampleKey(int x, int z) {
      int i = this.sampler.sample(x, z);

      return BiomeIdManager.getKey(i);
   }

   public Biome sample(Registry<Biome> biomeRegistry, int x, int z) {
      int i = this.sampler.sample(x, z);
      RegistryKey<Biome> registryKey = BiomeIdManager.getKey(i);

      if (registryKey == null) {
         throw new IllegalStateException("Unknown biome id emitted by layers: " + i);
      } else {
         Biome biome = biomeRegistry.get(registryKey);
         if (biome == null) {
            Util.error("Unknown biome id: " + i);
            return biomeRegistry.get(BiomeKeys.PLAINS);
         } else {
            return biome;
         }
      }
   }
}