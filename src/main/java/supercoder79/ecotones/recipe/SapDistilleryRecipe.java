package supercoder79.ecotones.recipe;

import net.minecraft.item.Item;

public record SapDistilleryRecipe(Item in, Item out, int color /* 0xRRGGBBB */) {
    public int r() {
        return (this.color & 0xFF0000) >> 16;
    }

    public int g() {
        return (this.color & 0x00FF00) >> 8;
    }

    public int b() {
        return (this.color & 0x0000FF) >> 0;
    }
}
