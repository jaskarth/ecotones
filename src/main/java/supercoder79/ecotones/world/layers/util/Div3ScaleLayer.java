package supercoder79.ecotones.world.layers.util;

import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

import java.util.HashMap;
import java.util.Map;

public enum Div3ScaleLayer implements ParentedLayer {
   INSTANCE;

   // Used to help avoid allocations
   private static final ThreadLocal<HashMap<Integer, Integer>> ACCUMULATOR = ThreadLocal.withInitial(HashMap::new);

   public int transformX(int x) {
      return x / 3;
   }

   public int transformZ(int z) {
      return z / 3;
   }

   public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      context.initSeed(x / 3, z / 3);

      HashMap<Integer, Integer> map = ACCUMULATOR.get();
      map.clear();

      for (int ax = 0; ax < 3; ax++) {
         for (int az = 0; az < 3; az++) {
            int i = parent.sample(transformX(x + ax), transformZ(z + az));

            int v = map.getOrDefault(i, 0) + 1;
            map.put(i, v);
         }
      }

      int id = -1;
      int count = -1;
      for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
         if (entry.getValue() > count) {
            count = entry.getValue();
            id = entry.getKey();
         }
      }

      map.clear();

      return id;
   }
}