package supercoder79.ecotones.fluid;

import com.mojang.blaze3d.systems.RenderSystem;

public class Fluid {
    private final Properties properties;

    public Fluid(Properties properties) {
        this.properties = properties;
    }

    public record Properties(
            String name, Color color, Color secondaryColor
    ) {

    }

    public void stateColor() {
        RenderSystem.setShaderColor(this.properties.color.r() / 255.f, this.properties.color.g() / 255.f, this.properties.color.b() / 255.f, 1.0F);
    }

    public static class Builder {
        private String name;
        private Color color;
        private Color secondaryColor;

        public Builder() {

        }

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder color(int r, int g, int b) {
            this.color = new Color(r << 16 | g << 8 | b);

            if (this.secondaryColor == null) {
                this.secondaryColor = this.color;
            }

            return this;
        }

        public Builder secondaryColor(int r, int g, int b) {
            this.secondaryColor = new Color(r << 16 | g << 8 | b);

            return this;
        }

        public Properties build() {
            return new Properties(this.name, this.color, this.secondaryColor);
        }
    }

    public record Color(int rgb) {
        public int r() {
            return (this.rgb >> 16) & 0xFF;
        }

        public int g() {
            return (this.rgb >> 8) & 0xFF;
        }

        public int b() {
            return (this.rgb) & 0xFF;
        }
    }
}
