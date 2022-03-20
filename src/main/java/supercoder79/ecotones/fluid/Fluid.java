package supercoder79.ecotones.fluid;

public class Fluid {
    private final Properties properties;

    public Fluid(Properties properties) {
        this.properties = properties;
    }

    public static record Properties(String name, int color) {

    }

    public static class Builder {
        private String name;
        private int color;

        public Builder() {

        }

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder color(int r, int g, int b) {
            this.color = r << 16 | g << 8 | b;
            return this;
        }

        public Properties build() {
            return new Properties(this.name, this.color);
        }
    }
}
