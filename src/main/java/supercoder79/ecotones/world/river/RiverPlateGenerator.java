package supercoder79.ecotones.world.river;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.CheckedRandom;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.world.layers.system.layer.util.CachingLayerSampler;
import supercoder79.ecotones.world.river.graph.RiverGraph;
import supercoder79.ecotones.world.river.graph.RiverNode;
import supercoder79.ecotones.world.river.graph.RiverPhiNode;
import supercoder79.ecotones.world.river.graph.RiverSubgraph;
import supercoder79.ecotones.world.river.phys.AABB;
import supercoder79.ecotones.world.river.phys.BruteforceIntersect;

import java.util.*;

// Where the actual river plate gets created
public final class RiverPlateGenerator {
    public static PlateSet generate(long seed, ChunkPos pos, CachingLayerSampler riverSampler) {
        LongSet plateChunks = new LongOpenHashSet();
        LongSet visited = new LongOpenHashSet();

        int sample = sampleChunkPos(pos.x, pos.z, riverSampler);
        // Empty
        if (sample == 0) {
            return PlateSet.OCEAN_MARKER;
        }

        // Find all chunks in plate
        dfs(pos.x, pos.z, sample, riverSampler, plateChunks, visited);

        // Testing seed: -4775535294389812203

        RiverGraph graph = runPilotRivers(seed, plateChunks);

        iterateGraphReach(seed, graph, plateChunks);

        // Branches
        traversePredecessors(seed, graph, plateChunks, 60);
        traversePredecessors(seed, graph, plateChunks, 48);
        traversePredecessors(seed, graph, plateChunks, 30);
//        traversePredecessors(seed, graph, plateChunks, 8);

        resetAllNodes(graph);

        growRiversDownstream(graph);

        return new PlateSet(plateChunks, sample, graph);
    }

    // Initial "pilot" stage where rivers identify major contours and destinations (lakes and such)
    private static RiverGraph runPilotRivers(long seed, LongSet plateChunks) {
        ImprovedChunkRandom random = new ImprovedChunkRandom((seed));

        RiverGraph graph = new RiverGraph();
        for (long pos : plateChunks) {
            int x = ChunkPos.getPackedX(pos);
            int z = ChunkPos.getPackedZ(pos);
            random.setCarverSeed(seed, x, z);

            // 1/2500 chance
            if (random.nextInt(2500) > 0) {
                continue;
            }

            int length = random.nextInt(45) + 50;

            // Random starting point
            double startX = (x * 16) + random.nextDouble(16);
            double startZ = (z * 16) + random.nextDouble(16);

            double angle = random.nextDouble(RiverAngleHelper.PI2);

            boolean builtPhi = false;
            RiverSubgraph subgraph = new RiverSubgraph();
            for (int i = 0; i < length; i++) {
                RiverNode node = new RiverNode(startX, startZ, 3, /*2 + (int)((i * 4.0) / length),*/ 2, angle);

                // Out of bounds!
                if (!plateChunks.contains(node.holdingChunkPos())) {
                    break;
                }

                if (subgraph.getCurrent() != null) {
                    AABB line = AABB.buildLine(subgraph.getCurrent(), node);

                    AABB clip = graph.getClip(line);
                    if (clip != null) {
                        // River merging bad- disable for the time being
                        if (true) {
                            subgraph = new RiverSubgraph();
                            break;
                        }

//                        RiverNode base = null;
//                        double minDist = Double.POSITIVE_INFINITY;
//
//                        for (RiverNode component : clip.getComponents()) {
//                            double dist = component.dist(node);
//
//                            if (minDist > dist) {
//                                minDist = dist;
//                                base = component;
//                            }
//                        }

//                        if (base != null) {
//                            boolean unnatural = RiverAngleHelper.isUnnaturalMerge(base, node);
//
//                            if (unnatural) {
//                                // Testing code, needs psi
////                                subgraph.addNode(node);
////                                node.addSuccessor(base);
//                            } else {
                                // Build phi node

                                // Find best intersection
                                // FIXME: slightly wrong! Needs to be closer in some cases and further in others!
                                // FIXME: https://discord.com/channels/651605124652859404/659536223156830210/930254922149134366
                                Vec2f intrsct = BruteforceIntersect.findBestIntersection(clip, line);

                                if (intrsct != null) {
                                    builtPhi = true;
//                                    System.out.println("phi");
                                    RiverPhiNode phi = new RiverPhiNode(intrsct.x, intrsct.y, 3, 2, angle);

                                    // TODO: adding to the wrong subgraph?
                                    subgraph.addNodeDirect(phi);

                                    // Add new node to graph as we break before that's done
                                    subgraph.addNode(node);

                                    // Branch->phi
                                    node.addSuccessor(phi);

                                    // Modify base branch to insert phi
                                    // TODO: move to rivernode?

                                    RiverNode first = clip.getComponents().get(0);
                                    RiverNode next = clip.getComponents().get(1);
                                    phi.next = next;

//                                    first.delSuccessor(next);
//                                    first.addSuccessor(phi);
//                                    phi.addSuccessor(next);
                                }
                                // TODO: base-node has no collider!
//                            }
//                        }

                        // TODO: angle based phi node
                        // TODO: psi node when angle inappropriate

                        break;
                    }

                    subgraph.addAABB(line);
                }

                subgraph.addNode(node);
                angle += (random.nextDouble() - random.nextDouble()) * (Math.PI / 6);

                // Normalize to [0, 2pi)
                angle = RiverAngleHelper.wrapRad(angle);

                double dist = random.nextDouble(20) + 15;
                startX += Math.cos(angle) * dist;
                startZ += Math.sin(angle) * dist;
            }

            subgraph.endsWithPhi = builtPhi;

            // Check if subgraph has any rivers above a certain size
            if (subgraph.getNodes().size() > 18 || subgraph.endsWithPhi) {
                graph.addSubgraph(subgraph);
            }
        }

        return graph;
    }

    private static void iterateGraphReach(long seed, RiverGraph graph, LongSet plateChunks) {
//        ChunkRandom random = new ChunkRandom(new CheckedRandom(seed));

        for (RiverSubgraph subgraph : graph.getSubgraphs()) {
            if (subgraph.endsWithPhi) {
                continue;
            }

            // Last node in line
            RiverNode last = subgraph.getCurrent();
            double angle = last.angle();

            double x = last.x() + Math.cos(angle) * 20;
            double z = last.z() + Math.sin(angle) * 20;

            RiverNode node = new RiverNode(x, z, 3, 2, angle);

            if (!plateChunks.contains(node.holdingChunkPos())) {
                break;
            }

            AABB line = AABB.buildLine(subgraph.getCurrent(), node);

            AABB clip = graph.getClip(line);
            if (clip != null) {
                Vec2f intrsct = BruteforceIntersect.findBestIntersection(clip, line);

                if (intrsct != null) {
                    RiverPhiNode phi = new RiverPhiNode(intrsct.x, intrsct.y, 3, 2, angle);

                    // TODO: adding to the wrong subgraph?
                    subgraph.addNodeDirect(phi);

                    // Add new node to graph as we break before that's done
                    subgraph.addNode(node);

                    // Branch->phi
                    node.addSuccessor(phi);

                    phi.next = clip.getComponents().get(1);
                }
            }
        }
    }

    private static void traversePredecessors(long seed, RiverGraph graph, LongSet plateChunks, int maxSize) {
        ImprovedChunkRandom random = new ImprovedChunkRandom((seed));

        int minSize = maxSize / 2;

        Deque<RiverNode> stack = new LinkedList<>();
        int i = 0;
        for (RiverSubgraph subgraph : graph.getSubgraphs()) {
            RiverNode current = subgraph.getCurrent();
            random.setCarverSeed(seed, (int)current.x(), (int)current.z());

            stack.add(current);

            // TODO: needs a good count stack
            int good = 0;
            int nextGood = 8 + random.nextInt(7);
            while (!stack.isEmpty()) {
                RiverNode node = stack.removeFirst();

                // Exactly 1 path
                if (node.getPredecessors().size() == 1) {
                    good++;
                } else {
                    good = 0;
                }

                // Build branch
                if (good >= nextGood) {
                    good = 0;
                    nextGood = 8 + random.nextInt(7);

                    // Branch tree
                    int len = random.nextInt(maxSize - minSize) + minSize;
                    len = Math.max(len, 6);

                    double x = node.x();
                    double z = node.z();

                    // FIXME: correct angle!
                    double angleAdd = (random.nextDouble() - random.nextDouble()) * (Math.PI / 3);
                    while (Math.abs(angleAdd) < (Math.PI / 5)) {
                        angleAdd = (random.nextDouble() - random.nextDouble()) * (Math.PI / 3);
                    }

                    angleAdd = Math.PI - angleAdd;

                    double angle = node.angle() + angleAdd;
                    angle = RiverAngleHelper.wrapRad(angle);

                    double dist = random.nextDouble(20) + 15;
                    x += Math.cos(angle) * dist;
                    z += Math.sin(angle) * dist;

                    RiverNode last = node;
                    Set<RiverNode> exclude = new HashSet<>();
                    exclude.add(last);
                    LinkedList<RiverNode> subgraphDirect = new LinkedList<>();
                    subgraphDirect.add(last);
                    for (int j = 0; j < len; j++) {
                        RiverNode branch = new RiverNode(x, z, 3, 2, angle);

                        if (!plateChunks.contains(branch.holdingChunkPos())) {
                            break;
                        }

                        AABB line = AABB.buildLine(last, branch);
                        if (graph.getClip(line, exclude) != null) {
                            // Phi not possible in limited predecessor traversal
                            subgraphDirect = new LinkedList<>();
                            break;
                        }

                        subgraphDirect.add(branch);

//                        subgraph.addAABB(line);
//
//                        branch.addSuccessor(last);
//
//                        subgraph.addNodeDirect(branch);

                        //////

                        angle += (random.nextDouble() - random.nextDouble()) * (Math.PI / 6);

                        // Normalize to [0, 2pi)
                        angle = RiverAngleHelper.wrapRad(angle);

                        dist = random.nextDouble(20) + 15;
                        x += Math.cos(angle) * dist;
                        z += Math.sin(angle) * dist;

                        last = branch;
                        exclude.add(last);
                    }

                    if (subgraphDirect.size() >= (minSize * 0.6)) {
                        for (RiverNode nd : subgraphDirect) {
                            if (nd == node) {
                                continue;
                            }

                            subgraph.addNodeDirect(nd);
                        }

                        for (int j = subgraphDirect.size() - 2; j >= 0; j--) {
                            RiverNode nd1 = subgraphDirect.get(j + 1);
                            RiverNode nd2 = subgraphDirect.get(j);
                            nd1.addSuccessor(nd2);

                            // Collider
                            AABB line = AABB.buildLine(nd1, nd2);
                            subgraph.addAABB(line);
                        }
                    }
                }

                stack.addAll(node.getPredecessors());
            }

            i++;
        }
    }

    private static void resetAllNodes(RiverGraph graph) {
        for (RiverSubgraph subgraph : graph.getSubgraphs()) {
            for (RiverNode node : subgraph.getNodes()) {
                node.setRadius(3);
            }
        }
    }

    private static void growRiversDownstream(RiverGraph graph) {
        // Root nodes not accurate at this point

        List<RiverNode> roots = new ArrayList<>();

        // Reverse traversal
        for (RiverSubgraph subgraph : graph.getSubgraphs()) {
            RiverNode current = subgraph.getCurrent();

            Deque<RiverNode> stack = new LinkedList<>();
            stack.add(current);

            while (!stack.isEmpty()) {
                RiverNode node = stack.removeFirst();

                // Starting point
                if (node.getPredecessors().isEmpty()) {
                    roots.add(node);
                }

                stack.addAll(node.getPredecessors());
            }
        }

        // Forward traversal
        for (RiverNode root : roots) {
            Deque<RiverNode> stack = new LinkedList<>();
            stack.add(root);

            Set<RiverNode> seen = new HashSet<>();
            while (!stack.isEmpty()) {
                RiverNode node = stack.removeFirst();

                if (seen.contains(node)) {
                    continue;
                }

                seen.add(node);
                node.setRadius(Math.min(7, node.radius() + 1));

                stack.addAll(node.getSuccessors());

                // FIXME: bad hack! phi node should have proper successor, but we don't do that at the moment!
                if (node instanceof RiverPhiNode phi) {
                    if (phi.next != null) {
                        stack.add(phi.next);
                    }
                }
            }
        }
    }

    private static void dfs(int x, int z, int check, CachingLayerSampler riverSampler, LongSet plateChunks, LongSet visited) {
        long longpos = ChunkPos.toLong(x, z);
        Deque<Long> next = new LinkedList<>();
        next.add(longpos);

        while (!next.isEmpty()) {
            long pos = next.removeFirst();

            if (visited.contains(pos)) {
                continue;
            }

            visited.add(pos);

            int ax = ChunkPos.getPackedX(pos);
            int az = ChunkPos.getPackedZ(pos);
            int sample = sampleChunkPos(ax, az, riverSampler);
            if (sample == check) {
                plateChunks.add(pos);

                // Iterate next chunks
                next.add(ChunkPos.toLong(ax + 1, az));
                next.add(ChunkPos.toLong(ax - 1, az));
                next.add(ChunkPos.toLong(ax, az + 1));
                next.add(ChunkPos.toLong(ax, az - 1));
            }
        }
    }

    private static int sampleChunkPos(int x, int z, CachingLayerSampler riverSampler) {
        // Center sampling
        return riverSampler.sample((x << 2) + 2, (z << 2) + 2);
    }
}
