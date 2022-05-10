package supercoder79.ecotones.world.structure.gen.layout.cell;

import supercoder79.ecotones.util.Vec2d;
import supercoder79.ecotones.util.Vec2i;

import java.util.List;

public abstract class FenceableCell extends Cell {
    private boolean isFenced;

    protected FenceableCell(Cell other) {
        super(other);
    }

    public boolean isFenced() {
        return isFenced;
    }

    public void setFenced(boolean fenced) {
        isFenced = fenced;
    }
}
