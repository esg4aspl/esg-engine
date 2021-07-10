package tr.edu.iyte.esg.esgbalancing;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;


import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Vertex;

public class BalancedESGGeneratorFromStronglyConnectedJgraph {

	/**
	 * Generates a balanced ESG from a strongly connected ESG. Matching shows the
	 * edges that should be added to ESG to make the degree of each vertex zero.
	 * 
	 * @param matching- MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge>
	 * @return balanced ESG
	 */
	public static Graph<Vertex, Edge> generateBalancedESGFromStronlgyConnectedESG(Graph<Vertex, Edge> jGraphStronglyConnectedESG,
			MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge> matching) {
		
		for (DefaultWeightedEdge defaultWeightedEdge : matching.getEdges()) {
			Vertex source = matching.getGraph().getEdgeSource(defaultWeightedEdge);
			Vertex target = matching.getGraph().getEdgeTarget(defaultWeightedEdge);

			Vertex realSource = getVertexByIDInJgraph(source.getID(),jGraphStronglyConnectedESG);
			Vertex realTarget = getVertexByIDInJgraph(target.getID(),jGraphStronglyConnectedESG);

			addNewEdgesToESGFromSourceToTarget(jGraphStronglyConnectedESG, realSource, realTarget);
		}

		return jGraphStronglyConnectedESG;
	}
		
	/**
	 * Adds new edges to given ESG that will be balanced. This edges are added
	 * through a path from source to target. The length of the path is weight.
	 * 
	 * @param balancedESG - ESG to be balanced
	 * @param source      - source vertex of the path
	 * @param target      - target vertex of the path
	 * @param weight      - length of the path
	 */
	private static void addNewEdgesToESGFromSourceToTarget(Graph<Vertex,Edge> jGraphStronglyConnectedESG, Vertex source, Vertex target) {		
		FloydWarshallShortestPaths<Vertex, Edge> floydWarshallShortestPaths = StronglyConnectedBalancedESGUtilities.getFloydWarshallShortestPaths();
		GraphPath<Vertex, Edge> path = floydWarshallShortestPaths.getPath(source, target);
		int lastEdgeID = jGraphStronglyConnectedESG.edgeSet().size();
		for (Edge edge : path.getEdgeList()) {
			Vertex sourceVertex = edge.getSource();
			Vertex targetVertex = edge.getTarget();
			Edge newEdge = new EdgeSimple(lastEdgeID++, sourceVertex, targetVertex );
			jGraphStronglyConnectedESG.addEdge(sourceVertex, targetVertex, newEdge);
		}

	}
	
	private static Vertex getVertexByIDInJgraph(int ID,Graph<Vertex, Edge> jGraphStronglyConnectedESG) {
		for (Vertex vertex : jGraphStronglyConnectedESG.vertexSet()) {
			if (ID == vertex.getID())
				return vertex;
		}
		return null;
	}

}
