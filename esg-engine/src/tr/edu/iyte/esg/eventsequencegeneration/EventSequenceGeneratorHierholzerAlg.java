package tr.edu.iyte.esg.eventsequencegeneration;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;

import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;

public class EventSequenceGeneratorHierholzerAlg {
	
	public EventSequenceGeneratorHierholzerAlg(){
		
	}
	
	public boolean isEulerianGraph(Graph<Vertex,Edge> graph) {

		HierholzerEulerianCycle<Vertex,Edge> hierholzerEulerianCycle = new HierholzerEulerianCycle<Vertex,Edge>();

		boolean isEulerianGraph = hierholzerEulerianCycle.isEulerian(graph);

		return isEulerianGraph;
	}
	
	public GraphPath<Vertex,Edge> getEulerianCycle(Graph<Vertex,Edge> graph){

		HierholzerEulerianCycle<Vertex,Edge> hierholzerEulerianCycle = new HierholzerEulerianCycle<Vertex,Edge>();

		GraphPath<Vertex,Edge> eulerianCycle = hierholzerEulerianCycle.getEulerianCycle(graph);

		return eulerianCycle;
	}

}