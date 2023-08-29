package com.jaskarth.ecotones.world.worldgen.structure;

/**
 * Extra control for structures that have terrain generating below them, to filter which pieces need terrain gen and which don't.
 */
public interface StructureTerrainControl {
    default boolean generateTerrainBelow() {
        return true;
    }
}
