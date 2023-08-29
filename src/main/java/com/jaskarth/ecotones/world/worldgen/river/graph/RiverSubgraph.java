package com.jaskarth.ecotones.world.worldgen.river.graph;

import com.jaskarth.ecotones.world.worldgen.river.phys.AABB;

import java.util.ArrayList;
import java.util.List;

// Directed acyclic path from root to end
public final class RiverSubgraph {
    private RiverNode root;
    private final List<RiverNode> nodes = new ArrayList<>();
    private RiverNode current;
    private final List<AABB> colliders = new ArrayList<>();
    public boolean endsWithPhi;

    public RiverSubgraph() {

    }

    public void addNode(RiverNode node) {
        if (this.root == null) {
            this.root = node;
        } else {
            this.current.addSuccessor(node);
        }

        this.current = node;
        this.nodes.add(node);
    }

    public void addNodeDirect(RiverNode node) {
        this.nodes.add(node);
    }

    public void addAABB(AABB aabb) {
        this.colliders.add(aabb);
    }

    public boolean anyClip(AABB aabb) {
        for (AABB node : this.colliders) {
            if (node.clip(aabb)) {
                return true;
            }
        }

        return false;
    }

    public AABB getClip(AABB aabb) {
        for (AABB node : this.colliders) {
            if (node.clip(aabb)) {
                return node;
            }
        }

        return null;
    }

    public List<RiverNode> getNodes() {
        return nodes;
    }

    public RiverNode getRoot() {
        return root;
    }

    public RiverNode getCurrent() {
        return current;
    }

    public List<AABB> colliders() {
        return colliders;
    }
}
