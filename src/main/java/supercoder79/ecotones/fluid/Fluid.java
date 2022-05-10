package supercoder79.ecotones.fluid;

import com.mojang.blaze3d.systems.RenderSystem;

public class Fluid {
    private final Properties properties;

    public Fluid(Properties properties) {
        this.properties = properties;
    }

    public static record Properties(String name, int color) {

        // TODO: fields?

        public int r() {
            return (this.color >> 16) & 0xFF;
        }

        public int g() {
            return (this.color >> 8) & 0xFF;
        }

        public int b() {
            return (this.color) & 0xFF;
        }
    }

    public void stateColor() {
        RenderSystem.setShaderColor(this.properties.r() / 255.f, this.properties.g() / 255.f, this.properties.b() / 255.f, 1.0F);
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
