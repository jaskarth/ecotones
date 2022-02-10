package supercoder79.ecotones.util;

public record Vec2d(double x, double y) {
    public static Vec2d of(double x, double y) {
        return new Vec2d(x, y);
    }
}
