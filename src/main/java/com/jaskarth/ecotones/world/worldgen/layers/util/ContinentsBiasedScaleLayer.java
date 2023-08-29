package com.jaskarth.ecotones.world.worldgen.layers.util;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.ParentedLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;

public enum ContinentsBiasedScaleLayer implements ParentedLayer {
   NORMAL,
   FUZZY {
      @Override
      protected int sample(LayerSampleContext<?> context, int tl, int tr, int bl, int br) {
         return context.choose(tl, tr, bl, br);
      }
   };

   private static final int field_31805 = 1;
   private static final int field_31806 = 1;

   @Override
   public int transformX(int x) {
      return x >> 1;
   }

   @Override
   public int transformZ(int y) {
      return y >> 1;
   }

   @Override
   public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      int i = parent.sample(this.transformX(x), this.transformZ(z));
      context.initSeed((long)(x >> 1 << 1), (long)(z >> 1 << 1));
      int j = x & 1;
      int k = z & 1;
      if (j == 0 && k == 0) {
         return i;
      } else {
         int l = parent.sample(this.transformX(x), this.transformZ(z + 1));
         int m = context.choose(i, l);
         if (j == 0 && k == 1) {
            return m;
         } else {
            int n = parent.sample(this.transformX(x + 1), this.transformZ(z));
            int o = context.choose(i, n);
            if (j == 1 && k == 0) {
               return o;
            } else {
               int p = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
               return this.sample(context, i, n, l, p);
            }
         }
      }
   }

   protected int sample(LayerSampleContext<?> context, int atl, int atr, int abl, int abr) {
      int tr = atr & 1;
      int tl = atl & 1;
      int br = abr & 1;
      int bl = abl & 1;

      if (tr == bl && bl == br) {
         return atr;
      } else if (tl == tr && tl == bl) {
         return atl;
      } else if (tl == tr && tl == br) {
         return atl;
      } else if (tl == bl && tl == br) {
         return atl;
      } else if (tl == tr && bl != br) {
         return atl;
      } else if (tl == bl && tr != br) {
         return atl;
      } else if (tl == br && tr != bl) {
         return atl;
      } else if (tr == bl && tl != br) {
         return atr;
      } else if (tr == br && tl != bl) {
         return atr;
      } else {
         return bl == br && tl != tr ? abl : context.choose(atl, atr, abl, abr);
      }
   }

   private static boolean equals(int a, int b) {
      return a == 0 ? b == 0 : b != 0;
   }

   private static boolean notEqual(int a, int b) {
      return a == 0 ? b != 0 : b == 0;
   }
}
