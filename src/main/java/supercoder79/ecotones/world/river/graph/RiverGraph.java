package supercoder79.ecotones.world.river.graph;

import supercoder79.ecotones.world.river.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Graph for a plate set
public final class RiverGraph {
    private final List<RiverSubgraph> subgraphs = new ArrayList<>();

    public RiverGraph() {

    }

    public void addSubgraph(RiverSubgraph subgraph) {
        subgraphs.add(subgraph);
    }

    public boolean anyClip(AABB aabb) {
        for (RiverSubgraph subgraph : this.subgraphs) {
            if (subgraph.anyClip(aabb)) {
                return true;
            }
        }

        return false;
    }

    public AABB getClip(AABB aabb) {
        for (RiverSubgraph subgraph : this.subgraphs) {
            AABB ret = subgraph.getClip(aabb);
            if (ret != null) {
                return ret;
            }
        }

        return null;
    }

    public AABB getClip(AABB aabb, RiverSubgraph exclude) {
        for (RiverSubgraph subgraph : this.subgraphs) {
            if (subgraph == exclude) {
                continue;
            }

            AABB ret = subgraph.getClip(aabb);
            if (ret != null) {
                return ret;
            }
        }

        return null;
    }

    public AABB getClip(AABB aabb, Set<RiverNode> exclude) {
        for (RiverSubgraph subgraph : this.subgraphs) {
            AABB ret = subgraph.getClip(aabb);
            if (ret != null) {
                if (containsAny(exclude, ret.getComponents())) {
                    continue;
                }

                return ret;
            }
        }

        return null;
    }

    private static <T> boolean containsAny(Set<T> set, List<T> other) {
        for (T t : other) {
            if (set.contains(t)) {
                return true;
            }
        }

        return false;
    }

    public List<RiverSubgraph> getSubgraphs() {
        return subgraphs;
    }
}
