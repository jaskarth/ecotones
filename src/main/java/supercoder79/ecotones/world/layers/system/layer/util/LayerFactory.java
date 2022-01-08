package supercoder79.ecotones.world.layers.system.layer.util;

public interface LayerFactory<A extends LayerSampler> {
   A make();
}
