package tr.edu.iyte.esg.esgbalancing;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.KuhnMunkresMinimalWeightBipartitePerfectMatching;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
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
	private static FloydWarshallShortestPaths<Vertex, Edge> floydWarshallShortestPaths;
	
	@SuppressWarnings("rawtypes")
	public static Map<String,GraphPath> getNamePathMap(){
		return namePathMap;
	}
	
	public static void setFloydWarshallShortestPaths(Graph<Vertex, Edge> jGraphStronglyConnectedESG) {
		floydWarshallShortestPaths = new FloydWarshallShortestPaths<Vertex, Edge>(
				jGraphStronglyConnectedESG);
	}

	public static FloydWarshallShortestPaths<Vertex, Edge> getFloydWarshallShortestPaths() {
		return floydWarshallShortestPaths;
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
			Graph<Vertex, Edge> jGraphStronglyConnectedESG, Set<Vertex> positiveDegreeVertexPartition,
			Set<Vertex> negativeDegreeVertexPartition) {
		
		Graph<Vertex, DefaultWeightedEdge> bipartiteGraph = new DirectedWeightedMultigraph<Vertex, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		for (Vertex positiveDegreeVertex : positiveDegreeVertexPartition) {
			bipartiteGraph.addVertex(positiveDegreeVertex);
			for (Vertex negativeDegreeVertex : negativeDegreeVertexPartition) {			
				String name = "<" + positiveDegreeVertex.getEvent().getName() + "-" + negativeDegreeVertex.getEvent().getName() + ">";	
				double weight = -1;
				if(nameWeightMap.containsKey(name)) {
					weight = nameWeightMap.get(name);
				}else {
					weight = floydWarshallShortestPaths.getPathWeight(positiveDegreeVertex, negativeDegreeVertex);
					nameWeightMap.put(name, weight);
				}
				bipartiteGraph.addVertex(negativeDegreeVertex);
				DefaultWeightedEdge edge = bipartiteGraph.addEdge(positiveDegreeVertex, negativeDegreeVertex);
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
	
		

}
