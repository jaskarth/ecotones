package supercoder79.ecotones.world.river;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.math.ChunkPos;
import supercoder79.ecotones.world.river.graph.RiverGraph;
import supercoder79.ecotones.world.river.graph.RiverNode;
import supercoder79.ecotones.world.river.graph.RiverSubgraph;

import java.util.ArrayList;
import java.util.List;

public final class PlateSet {
    public static final PlateSet OCEAN_MARKER = new PlateSet(new LongOpenHashSet(), 0, new RiverGraph());
    private final LongSet chunkPositions;
    public final int color;
    private final RiverGraph graph;
    private Phase phase = Phase.EMPTY;

    public PlateSet(LongSet chunkPositions, int color, RiverGraph graph) {
        this.chunkPositions = chunkPositions;
        this.color = color;
        this.graph = graph;
    }

    public boolean hasChunk(long chunkPos) {
        return chunkPositions.contains(chunkPos);
    }

    public RiverGraph getGraph() {
        return graph;
    }

    public List<RiverNode> findForChunk(ChunkPos pos) {
        List<RiverNode> list = new ArrayList<>();

        for (RiverSubgraph subgraph : this.graph.getSubgraphs()) {
            for (RiverNode node : subgraph.getNodes()) {
                if (node.x() >= (pos.x-3) * 16 && node.x() <= (pos.x+3) * 16 && node.z() >= (pos.z-3) * 16 && node.z() <= (pos.z+3) * 16) {
                    list.add(node);
                }
            }
        }

        return list;
    }

    public void insertChunk(long chunkPos) {
        chunkPositions.add(chunkPos);
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public enum Phase {
        EMPTY,
        FLOOD_FILLING,
        RIVER_RUNNING,
        FULL
    }
}
