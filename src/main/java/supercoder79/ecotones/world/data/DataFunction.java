package supercoder79.ecotones.world.data;

@FunctionalInterface
public interface DataFunction {
    DataFunction NOOP = (x, z) -> 0;

    double get(double x, double z);
}
