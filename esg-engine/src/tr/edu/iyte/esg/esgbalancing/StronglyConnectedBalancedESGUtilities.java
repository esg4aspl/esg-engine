package tr.edu.iyte.esg.esgbalancing;

import java.util.Collections;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.matching.KuhnMunkresMinimalWeightBipartitePerfectMatching;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Vertex;

public class StronglyConnectedBalancedESGUtilities {
	
	
	@SuppressWarnings("rawtypes")
	private static Map<String,GraphPath> namePathMap = new LinkedHashMap<String, GraphPath>();
	private static Map<String,Double> nameWeightMap = new LinkedHashMap<String, Double>();
	private static ShortestPathProvider  shortestPathProvider;
	
	@SuppressWarnings("rawtypes")
	public static Map<String,GraphPath> getNamePathMap(){
		return namePathMap;
	}
	
    public static void initShortestPaths(Graph<Vertex, Edge> graph) {
        shortestPathProvider = new ShortestPathProvider(graph);
    }

    public static ShortestPathProvider getShortestPathProvider() {
        return shortestPathProvider;
    }
		
	public static ESG transformESGtoStronglyConnectedESG(ESG ESG) {
		Edge backwardEdge = new EdgeSimple(ESG.getNextEdgeID(),
				ESG.getPseudoEndVertex(),
				ESG.getPseudoStartVertex());
		ESG.addEdge(backwardEdge);
		return ESG;
	}
	
	/**
	 * Generates a bipartite graph by using the given positive and negative degree vertex partitions.
	 * 
	 * @param positiveDegreeVertexPartitionForBipartiteMatching
	 * @param negativeDegreeVertexPartitionForBipartiteMatching
	 * @param costMatrix - determines the weights between partition vertices
	 * @return bipartiteGraph - Graph<Vertex, DefaultWeightedEdge> 
	 */
	public static Graph<Vertex, DefaultWeightedEdge> generateBipartiteGraph(
	        Graph<Vertex, Edge> jGraphStronglyConnectedESG,
	        Set<Vertex> positiveDegreeVertexPartition,
	        Set<Vertex> negativeDegreeVertexPartition) {

	    return generateBipartiteGraph(
	            jGraphStronglyConnectedESG,
	            positiveDegreeVertexPartition,
	            negativeDegreeVertexPartition,
	            Collections.emptyMap());
	}

	/**
	 * New version: supports cloned vertices by using cloneToOriginal to ask
	 * Floyd–Warshall on the original vertices.
	 */
	public static Graph<Vertex, DefaultWeightedEdge> generateBipartiteGraph(
	        Graph<Vertex, Edge> jGraphStronglyConnectedESG,
	        Set<Vertex> positiveDegreeVertexPartition,
	        Set<Vertex> negativeDegreeVertexPartition,
	        Map<Vertex, Vertex> cloneToOriginal) {

	    Graph<Vertex, DefaultWeightedEdge> bipartiteGraph =
	        new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

	    nameWeightMap.clear();

	    for (Vertex positiveClone : positiveDegreeVertexPartition) {
//	        System.out.println("positiveDegreeVertex " + positiveClone.getEvent().getName()+ " " + positiveClone.getDegree());
	        bipartiteGraph.addVertex(positiveClone);

	        for (Vertex negativeClone : negativeDegreeVertexPartition) {
//	            System.out.println("negativeDegreeVertex " + negativeClone.getEvent().getName()+ " " + negativeClone.getDegree());

	            Vertex posOriginal = cloneToOriginal.getOrDefault(positiveClone, positiveClone);
	            Vertex negOriginal = cloneToOriginal.getOrDefault(negativeClone, negativeClone);

	            String key = "<" + posOriginal.getID() + "-" + negOriginal.getID() + ">";
	            double weight;

	            if (nameWeightMap.containsKey(key)) {
	                weight = nameWeightMap.get(key);
//	                System.out.println("if weight " + weight);
	            } else {
	                weight = shortestPathProvider.getPathWeight(posOriginal, negOriginal);
	                nameWeightMap.put(key, weight);
//	                System.out.println("else weight " + weight);
	            }

	            bipartiteGraph.addVertex(negativeClone);
	            DefaultWeightedEdge edge =
	                bipartiteGraph.addEdge(positiveClone, negativeClone);
	            bipartiteGraph.setEdgeWeight(edge, weight);
	        }
	    }

	    return bipartiteGraph;
	}

		
	/**
	 * Applies Khun-Munkres Algortihm or so-called Hungarian Method.  
	 * @param bipartiteGraph
	 * @param positiveDegreeVertexPartitionForBipartiteMatching
	 * @param negativeDegreeVertexPartitionForBipartiteMatching
	 * @return matching - minimum cost assignments between vertices
	 */
	public static MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge> getMatching(Graph<Vertex, DefaultWeightedEdge> bipartiteGraph,
			Set<Vertex> positiveDegreeVertexPartitionForBipartiteMatching,
			Set<Vertex> negativeDegreeVertexPartitionForBipartiteMatching ){
		
		KuhnMunkresMinimalWeightBipartitePerfectMatching<Vertex, DefaultWeightedEdge> hungarianAlgorithm = new KuhnMunkresMinimalWeightBipartitePerfectMatching<Vertex, DefaultWeightedEdge>(
				bipartiteGraph, positiveDegreeVertexPartitionForBipartiteMatching, negativeDegreeVertexPartitionForBipartiteMatching);

		MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge> matching = hungarianAlgorithm.getMatching();
		return matching;
	}
	
	
    public static class ShortestPathProvider {

        private final Graph<Vertex, Edge> graph;
        private final DijkstraShortestPath<Vertex, Edge> dijkstra;
        private final Map<Vertex, SingleSourcePaths<Vertex, Edge>> cache = new LinkedHashMap<>();

        public ShortestPathProvider(Graph<Vertex, Edge> graph) {
            this.graph = graph;
            this.dijkstra = new DijkstraShortestPath<>(graph);
        }

        private SingleSourcePaths<Vertex, Edge> getPathsFrom(Vertex source) {
            return cache.computeIfAbsent(source, dijkstra::getPaths);
        }

        public double getPathWeight(Vertex source, Vertex target) {
            if (!graph.containsVertex(source) || !graph.containsVertex(target)) {
                return Double.POSITIVE_INFINITY;
            }
            SingleSourcePaths<Vertex, Edge> paths = getPathsFrom(source);
            double w = paths.getWeight(target);
            return w;
        }

        public GraphPath<Vertex, Edge> getPath(Vertex source, Vertex target) {
            if (!graph.containsVertex(source) || !graph.containsVertex(target)) {
                return null;
            }
            SingleSourcePaths<Vertex, Edge> paths = getPathsFrom(source);
            return paths.getPath(target);
        }
    }
	
		

}