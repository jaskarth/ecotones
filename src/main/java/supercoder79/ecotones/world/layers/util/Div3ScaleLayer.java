package supercoder79.ecotones.world.layers.util;

import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteOpenHashMap;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

import java.util.HashMap;
import java.util.Map;

public enum Div3ScaleLayer implements ParentedLayer {
   INSTANCE;

   // Used to help avoid allocations
   private static final ThreadLocal<Short2ByteOpenHashMap> ACCUMULATOR = ThreadLocal.withInitial(Short2ByteOpenHashMap::new);
   // TODO: limits biome ids to 32767. I don't think this will be a problem, but just a reminder :P

   public int transformX(int x) {
      return x / 3;
   }

   public int transformZ(int z) {
      return z / 3;
   }

   public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      context.initSeed(x / 3, z / 3);

      Short2ByteOpenHashMap map = ACCUMULATOR.get();
      map.clear();

      for (int ax = 0; ax < 3; ax++) {
         for (int az = 0; az < 3; az++) {
            int i = parent.sample(transformX(x + ax), transformZ(z + az));

            int v = map.getOrDefault((short) i, (byte) 0) + 1;
            map.put((short) i, (byte) v);
         }
      }

      int id = -1;
      int count = -1;
      for (Short2ByteMap.Entry entry : map.short2ByteEntrySet()) {
         if (entry.getShortKey() > count) {
            count = entry.getByteValue();
            id = entry.getShortKey();
         }
      }

      map.clear();

      return id;
   }
}