package com.jaskarth.ecotones.world.worldgen.river.graph;

import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;

public class RiverNode {
    private final double x;
    private final double z;
    private double radius;
    private final double depth;
    private final double angle;

    protected final List<RiverNode> successors = new ArrayList<>();
    protected List<RiverNode> predecessors = new ArrayList<>();

    public RiverNode(double x, double z, double radius, double depth, double angle) {
        this.x = x;
        this.z = z;
        this.radius = radius;
        this.depth = depth;
        this.angle = angle;
    }

    public void addSuccessor(RiverNode node) {
        this.successors.add(node);
        node.predecessors.add(this);
    }

    public void delSuccessor(RiverNode node) {
        this.successors.remove(node);
        node.predecessors.remove(this);
    }

    public List<RiverNode> getSuccessors() {
        return successors;
    }

    public List<RiverNode> getPredecessors() {
        return predecessors;
    }

    public long holdingChunkPos() {
        return ChunkPos.toLong(((int)x) >> 4, ((int)z) >> 4);
    }

    public double distSqr(RiverNode node) {
        double nx = (this.x - node.x);
        double nz = (this.z - node.z);
        return nx * nx + nz * nz;
    }

    public double dist(RiverNode node) {
        return Math.sqrt(distSqr(node));
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double x() {
        return x;
    }

    public double z() {
        return z;
    }

    public double radius() {
        return radius;
    }

    public double depth() {
        return depth;
    }

    public double angle() {
        return angle;
    }
}
