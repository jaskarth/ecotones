package supercoder79.ecotones.util;

public record Vec2i(int x, int y) {
    public static Vec2i of(int x, int y) {
        return new Vec2i(x, y);
    }
}
