package com.jaskarth.ecotones.world.worldgen.layers.util;

import net.minecraft.util.Identifier;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.DiagonalCrossSamplingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.Ecotones;

public enum BiomeEdgeEnsureLayer implements DiagonalCrossSamplingLayer {
    INSTANCE;

    public static final Identifier ID = new Identifier("ecotones", "chasm");
    public static final Identifier EDGE = new Identifier("ecotones", "chasm_edge");

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int chasm = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
        int chasmEdge = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(EDGE));

        if (n == chasm || e == chasm || s == chasm || w == chasm) {
            if (n == chasm && e == chasm && s == chasm && w == chasm) {
                return center;
            }
            return chasmEdge;
        }

        return center;
    }
}
