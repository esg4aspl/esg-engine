package tr.edu.iyte.esg.testgeneration.faulty;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import tr.edu.iyte.esg.conversion.ESGToJgraphConverter;
import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;


public class FaultyEventSequenceGenerator {

	private ESG ESG;

	public FaultyEventSequenceGenerator(ESG ESG) {
		this.ESG = ESG;
	}

	public Set<EventSequence> generateFaultyEventSequenceSet() {
		List<EventSequence> eventSequenceList = generateShortestPathForEachVertex();
		List<Edge> faultyEdgeList = getFaultyEdgeList();
		
		Set<EventSequence> faultyEventSequenceSet = new LinkedHashSet<EventSequence>();
		
		for (EventSequence eventSequence : eventSequenceList) {
			String lastEventName = eventSequence.getEndVertex().getEvent().getName();
//System.out.println("Event Sequence " + eventSequence.toString());
//System.out.println("lastEventName " + lastEventName);
			for (Edge edge : faultyEdgeList) {			
				if (edge.getSource().getEvent().getName().equals(lastEventName)) {
//System.out.println("source " + edge.getSource().getEvent().getName());
					if (edge.getTarget().isPseudoEndVertex()) {
//System.out.println("target " + edge.getTarget().getEvent().getName());
						List<Vertex> eventList = new LinkedList<Vertex>();
						eventList.add(edge.getSource());
						EventSequence newEventSequence = new EventSequence();
						newEventSequence.setEventSequence(eventList);
						if(!faultyEventSequenceSet.contains(newEventSequence))
							faultyEventSequenceSet.add(newEventSequence);
					} else {
						Vertex target = edge.getTarget();
//System.out.println("target " + edge.getTarget().getEvent().getName());
						List<Vertex> es =   new LinkedList<Vertex>(eventSequence.getEventSequence());
						es.add(target);
						EventSequence newEventSequence2 = new EventSequence();
						newEventSequence2.setEventSequence(es);
//System.out.println("original ES " + eventSequence.toString());
//System.out.println("faulty ES " + newEventSequence2.toString());
						if(!faultyEventSequenceSet.contains(newEventSequence2))
							faultyEventSequenceSet.add(newEventSequence2);
					}
				}
			}
		}
		return faultyEventSequenceSet;
	}

	private List<Edge> getFaultyEdgeList() {
		InverseESG inverseESG = new InverseESG(ESG);
		List<Edge> faultyEdgeList = inverseESG.getEdgeList();
		//faultyEdgeList.forEach(e->System.out.println(e.toString()));
		//System.out.println("----------------------------------------------------------------------");
		return faultyEdgeList;
	}

	private List<EventSequence> generateShortestPathForEachVertex() {

		ESGToJgraphConverter jGraphConverter = new ESGToJgraphConverter();
		Graph<Vertex, Edge> jGraphESG = jGraphConverter.buildJGraphFromESG(ESG);
		DijkstraShortestPath<Vertex, Edge> dijkstraShortestPath = new DijkstraShortestPath<Vertex, Edge>(jGraphESG);

		List<EventSequence> eventSequenceList = new LinkedList<EventSequence>();

		for (Vertex vertex : ESG.getRealVertexList()) {
			GraphPath<Vertex, Edge> shortestPath = dijkstraShortestPath.getPath(ESG.getPseudoStartVertex(), vertex);
			EventSequence eventSequence = graphPathToEventSequence(shortestPath);
			
			eventSequenceList.add(eventSequence);
		}

		return eventSequenceList;

	}

	private EventSequence graphPathToEventSequence(GraphPath<Vertex, Edge> shortestPath) {

		List<Vertex> vertexList = shortestPath.getVertexList();
		EventSequence eventSequence = new EventSequence();
		eventSequence.setEventSequence(vertexList.subList(1, vertexList.size()));
		//System.out.println(eventSequence.toString());
		
		return eventSequence;
	}

}
