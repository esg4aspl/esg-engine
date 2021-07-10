package tr.edu.iyte.esg.conversion;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.Vertex;

public class ESGToJgraphConverter {

	public ESGToJgraphConverter() {

	}

	public Graph<Vertex, DefaultWeightedEdge> buildDirectedWeightedJGraphFromESG(ESG ESG) {

		List<Vertex> vertexList = ESG.getVertexList();
		List<Edge> edgeList = ESG.getEdgeList();
		Graph<Vertex, DefaultWeightedEdge> jGraph = new SimpleDirectedWeightedGraph<Vertex, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		for (Vertex v : vertexList) {
			jGraph.addVertex(v);
		}
		for (Edge e : edgeList) {
			DefaultWeightedEdge dwe = new DefaultWeightedEdge();
			jGraph.addEdge(e.getSource(), e.getTarget(),dwe);
			jGraph.setEdgeWeight(dwe, 1.0);
		}

		return jGraph;
	}

	public Graph<Vertex, Edge> buildJGraphFromESG(ESG ESG) {

		List<Vertex> vertexList = ESG.getVertexList();
		List<Edge> edgeList = ESG.getEdgeList();
		Graph<Vertex, Edge> jGraph = new DirectedPseudograph<>(Edge.class);

		for (Vertex v : vertexList) {
			jGraph.addVertex(v);
		}
		for (Edge e : edgeList) {
			jGraph.addEdge(e.getSource(), e.getTarget(), e);
		}

		return jGraph;
	}

	public List<Graph<Vertex, Edge>> buildJGraphListFromModel(Model model) {

		List<Graph<Vertex, Edge>> graphList = new ArrayList<Graph<Vertex, Edge>>();

		for (ESG ESG : model.getEsgList()) {
			Graph<Vertex, Edge> jGraph = buildJGraphFromESG(ESG);
			graphList.add(jGraph);
		}

		return graphList;
	}

}
