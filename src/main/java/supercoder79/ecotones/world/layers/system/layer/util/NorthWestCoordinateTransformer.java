package supercoder79.ecotones.world.layers.system.layer.util;

public interface NorthWestCoordinateTransformer extends CoordinateTransformer {
   @Override
   default int transformX(int x) {
      return x - 1;
   }

   @Override
   default int transformZ(int y) {
      return y - 1;
   }
}
