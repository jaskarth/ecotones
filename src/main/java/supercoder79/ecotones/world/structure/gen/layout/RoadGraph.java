package supercoder79.ecotones.world.structure.gen.layout;

import supercoder79.ecotones.util.Vec2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Graph roads in a cell
// TODO: how to represent road connections between cells?
//   -> idea: use a representation of shared entries and exits
public final class RoadGraph {
    private final Node start;
    private final List<Node> nodes = new ArrayList<>();

    public RoadGraph(Vec2i start) {
        this.start = new Node(start);
    }

    // Maybe at this point it'd be worth having an abstract graph node
    public static class Node {
        private final Vec2i pos;
        private final List<Node> successors = new ArrayList<>();
        private final List<Node> predecessors = new ArrayList<>();

        public Node(Vec2i pos) {
            this.pos = pos;
        }

        public void addSuccessor(Node node) {
            this.successors.add(node);
            node.predecessors.add(this);
        }

        public void delSuccessor(Node node) {
            this.successors.remove(node);
            node.predecessors.remove(this);
        }

        public List<Node> getSuccessors() {
            return successors;
        }

        public List<Node> getPredecessors() {
            return predecessors;
        }
    }
}
