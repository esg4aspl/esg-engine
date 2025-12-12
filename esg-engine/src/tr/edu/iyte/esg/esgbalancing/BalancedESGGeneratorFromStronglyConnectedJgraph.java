package tr.edu.iyte.esg.esgbalancing;

import java.util.Map;


import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DefaultWeightedEdge;

import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Vertex;

public class BalancedESGGeneratorFromStronglyConnectedJgraph {

    /**
     * Generate a balanced ESG from a strongly connected ESG and a bipartite
     * matching.
     *
     * @param stronglyConnectedESG the original strongly connected ESG (JGraphT graph)
     * @param matching             minimal-cost matching between positive/negative partitions
     * @param cloneToOriginal      map from clone vertex -> original vertex
     */
    public static Graph<Vertex, Edge> generateBalancedESGFromStronlgyConnectedESG(
            Graph<Vertex, Edge> stronglyConnectedESG,
            MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge> matching,
            Map<Vertex, Vertex> cloneToOriginal) {

        Graph<Vertex, Edge> balancedESG = new DirectedPseudograph<>(Edge.class);

        // Copy vertices
        for (Vertex v : stronglyConnectedESG.vertexSet()) {
            balancedESG.addVertex(v);
        }

        // Copy existing edges
        for (Edge e : stronglyConnectedESG.edgeSet()) {
            balancedESG.addEdge(e.getSource(), e.getTarget(), e);
        }

        StronglyConnectedBalancedESGUtilities.ShortestPathProvider sp =
        	    StronglyConnectedBalancedESGUtilities.getShortestPathProvider();


        // Edge ID mechanism:
        // IDs are int, start at 0, and next ID is "current number of edges".
        // Original graph has N edges -> IDs [0..N-1].
        // Next new edge gets ID = N, then N+1, etc.
        int nextEdgeId = stronglyConnectedESG.edgeSet().size();

        // For each matching edge between partitions, add shortest-path edges
        for (DefaultWeightedEdge mEdge : matching.getEdges()) {
            Vertex posClone = matching.getGraph().getEdgeSource(mEdge);
            Vertex negClone = matching.getGraph().getEdgeTarget(mEdge);

            Vertex posOriginal = cloneToOriginal.getOrDefault(posClone, posClone);
            Vertex negOriginal = cloneToOriginal.getOrDefault(negClone, negClone);

            nextEdgeId = addNewEdgesToESGFromSourceToTarget(
                    balancedESG, sp, posOriginal, negOriginal, nextEdgeId);
        }

        return balancedESG;
    }

    /**
     * Adds a copy of the shortest path from source to target (original vertices!)
     * to the ESG and returns the updated nextEdgeId.
     */
    private static int addNewEdgesToESGFromSourceToTarget(
            Graph<Vertex, Edge> balancedESG,
            StronglyConnectedBalancedESGUtilities.ShortestPathProvider sp,
            Vertex sourceOrig,
            Vertex targetOrig,
            int nextEdgeIdStart) {

    	GraphPath<Vertex, Edge> path = sp.getPath(sourceOrig, targetOrig);
        if (path == null || path.getEdgeList().isEmpty()) {
//            System.out.println("No path found between " + sourceOrig + " and " + targetOrig + " – skipping.");
            return nextEdgeIdStart;
        }

        int nextEdgeId = nextEdgeIdStart;

        for (Edge eOrig : path.getEdgeList()) {
            Vertex u = eOrig.getSource();
            Vertex v = eOrig.getTarget();

            int currentId = nextEdgeId++;
            Edge newEdge = new EdgeSimple(currentId, u, v);

            balancedESG.addEdge(newEdge.getSource(), newEdge.getTarget(), newEdge);

        }

        return nextEdgeId;
    }
}
