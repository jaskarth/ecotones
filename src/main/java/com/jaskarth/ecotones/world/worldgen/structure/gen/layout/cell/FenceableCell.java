package com.jaskarth.ecotones.world.worldgen.structure.gen.layout.cell;

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
