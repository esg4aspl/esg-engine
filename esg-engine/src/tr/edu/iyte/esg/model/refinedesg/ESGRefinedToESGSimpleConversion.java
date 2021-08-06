package tr.edu.iyte.esg.model.refinedesg;

import java.util.Set;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedByESG;
import tr.edu.iyte.esg.model.VertexSimple;

public class ESGRefinedToESGSimpleConversion {

	public ESG convertESGRefinedToESGSimple(ESG ESGRefined) {

		ESG ESGSimple = new ESG(ESGRefined.getID(), ESGRefined.getName());
		addAllVerticesExceptRefinedVertices(ESGRefined, ESGSimple);
		addSubESGRealEdges(ESGRefined, ESGSimple);
		connectRefinedVertexEdgesWithSubESGEdges(ESGRefined, ESGSimple);

		return ESGSimple;
	}
	
	private void addAllVerticesExceptRefinedVertices(ESG ESGRefined, ESG ESGSimple) {
		for (Vertex vertex : ESGRefined.getVertexList()) {
			if (vertex instanceof VertexRefinedByESG) {
				ESG subESG = ((VertexRefinedByESG) vertex).getSubESG();
				for (Vertex subVertex : subESG.getRealVertexList()) {
					String subEventName = subVertex.getEvent().getName();
					Event eventSimple = new EventSimple(ESGSimple.getNextEventID(), subEventName);
					ESGSimple.addEvent(eventSimple);
					Vertex vertexSimple = new VertexSimple(ESGSimple.getNextVertexID(), eventSimple);
					ESGSimple.addVertex(vertexSimple);
				}
			} else {
				String eventName = vertex.getEvent().getName();
				Event eventSimple = new EventSimple(ESGSimple.getNextEventID(), eventName);
				ESGSimple.addEvent(eventSimple);
				Vertex vertexSimple = new VertexSimple(ESGSimple.getNextVertexID(), eventSimple);
				ESGSimple.addVertex(vertexSimple);
			}
		}
	}
	private void addSubESGRealEdges(ESG ESGRefined, ESG ESGSimple) {
		for (Vertex vertex : ESGRefined.getVertexList()) {
			if (vertex instanceof VertexRefinedByESG) {
				ESG subESG = ((VertexRefinedByESG) vertex).getSubESG();
				for (Edge subEdge : subESG.getRealEdgeList()) {
					Vertex sourceVertex = subEdge.getSource();
					Event sourceEvent = sourceVertex.getEvent();
					String sourceEventName = sourceEvent.getName();

					Vertex targetVertex = subEdge.getTarget();
					Event targetEvent = targetVertex.getEvent();
					String targetEventName = targetEvent.getName();

					Vertex currentSourceVertex = ESGSimple.getVertexByEventName(sourceEventName);
					Vertex currentTargetVertex = ESGSimple.getVertexByEventName(targetEventName);
					Edge currentEdge = new EdgeSimple(ESGSimple.getNextEdgeID(), currentSourceVertex,
							currentTargetVertex);
					ESGSimple.addEdge(currentEdge);
				}
			}
		}
	}

	private void connectRefinedVertexEdgesWithSubESGEdges(ESG ESGRefined, ESG ESGSimple) {

		for (Edge edge : ESGRefined.getEdgeList()) {
			Vertex sourceVertex = edge.getSource();
			Event sourceEvent = sourceVertex.getEvent();
			String sourceEventName = sourceEvent.getName();

			Vertex targetVertex = edge.getTarget();
			Event targetEvent = targetVertex.getEvent();
			String targetEventName = targetEvent.getName();

			if ((sourceVertex instanceof VertexSimple) && (targetVertex instanceof VertexSimple)) {
				//System.out.println("VertexSimple - VertexSimple");
				Vertex currentSourceVertex = ESGSimple.getVertexByEventName(sourceEventName);
				Vertex currentTargetVertex = ESGSimple.getVertexByEventName(targetEventName);
				Edge currentEdge = new EdgeSimple(ESGSimple.getNextEdgeID(), currentSourceVertex, currentTargetVertex);
				ESGSimple.addEdge(currentEdge);
			}else if ((edge.getSource() instanceof VertexRefinedByESG)
					&& (edge.getTarget() instanceof VertexRefinedByESG)) {
				//System.out.println("VertexRefinedByESG - VertexRefinedByESG");
				ESG sourceSubESG = ((VertexRefinedByESG) sourceVertex).getSubESG();
				Set<Vertex> sourceSubESGExitVertexSet = sourceSubESG.getExitVertexSet();

				ESG targetSubESG = ((VertexRefinedByESG) targetVertex).getSubESG();
				Set<Vertex> targetSubESGEntryVertexSet = targetSubESG.getEntryVertexSet();

				for (Vertex sourceSubESGExitVertex : sourceSubESGExitVertexSet) {
					for (Vertex targetSubESGEntryVertex : targetSubESGEntryVertexSet) {
						Event sourceSubESGExitEvent = sourceSubESGExitVertex.getEvent();
						String sourceSubESGExitEventName = sourceSubESGExitEvent.getName();
						Event targetSubESGEntryEvent = targetSubESGEntryVertex.getEvent();
						String targetSubESGEntryEventName = targetSubESGEntryEvent.getName();
						Vertex currentSourceVertex = ESGSimple.getVertexByEventName(sourceSubESGExitEventName);
						Vertex currentTargetVertex = ESGSimple.getVertexByEventName(targetSubESGEntryEventName);
						Edge currentEdge = new EdgeSimple(ESGSimple.getNextEdgeID(), currentSourceVertex,
								currentTargetVertex);
						ESGSimple.addEdge(currentEdge);
					}
				}
			}else if ((edge.getSource() instanceof VertexSimple) && (edge.getTarget() instanceof VertexRefinedByESG)) {
				//System.out.println("VertexSimple - VertexRefinedByESG");
				Vertex currentSourceVertex = ESGSimple.getVertexByEventName(sourceEventName);
				ESG subESG = ((VertexRefinedByESG) targetVertex).getSubESG();
				Set<Vertex> subESGEntryVertexSet = subESG.getEntryVertexSet();
				for (Vertex subEntryVertex : subESGEntryVertexSet) {
					String subEntryVertexEventName = subEntryVertex.getEvent().getName();
					Vertex subEntryVertexInESGSimple = ESGSimple.getVertexByEventName(subEntryVertexEventName);
					Edge currentEdge = new EdgeSimple(ESGSimple.getNextEdgeID(), currentSourceVertex,
							subEntryVertexInESGSimple);
					ESGSimple.addEdge(currentEdge);
				}
			}else if ((edge.getSource() instanceof VertexRefinedByESG) && (edge.getTarget() instanceof VertexSimple)) {
				//System.out.println("VertexRefinedByESG - VertexSimple");
				Vertex currentTargetVertex = ESGSimple.getVertexByEventName(targetEventName);
				ESG subESG = ((VertexRefinedByESG) sourceVertex).getSubESG();
				Set<Vertex> subESGExitVertexSet = subESG.getExitVertexSet();
				for (Vertex subExitVertex : subESGExitVertexSet) {
					String subExitVertexEventName = subExitVertex.getEvent().getName();
					Vertex subExitVertexInESGSimple = ESGSimple.getVertexByEventName(subExitVertexEventName);
					Edge currentEdge = new EdgeSimple(ESGSimple.getNextEdgeID(), subExitVertexInESGSimple,
							currentTargetVertex);
					ESGSimple.addEdge(currentEdge);
				}
			}

		}
	}

}
