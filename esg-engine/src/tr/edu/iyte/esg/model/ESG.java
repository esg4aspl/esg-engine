package tr.edu.iyte.esg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tr.edu.iyte.esg.model.decisiontable.DecisionTable;

public class ESG {

	final private int ID;
	final private String name;
	private int lastVertexID, lastEdgeID, lastEventID, lastDecisionTableID, lastSubEsgID;
	private List<Vertex> vertexList;
	private List<Edge> edgeList;
	private List<Event> eventList;
	private Map<Vertex, DecisionTable> decisionTableMap;
	// TODO private List<Label> labelList;
	private Map<Vertex, Set<Vertex>> vertexMap;
	private Set<Vertex> entryVertexSet;
	private Set<Vertex> exitVertexSet;

	public ESG(int ID, String name) {
		this.ID = ID;
		this.name = name;
		vertexList = new ArrayList<Vertex>();
		edgeList = new ArrayList<Edge>();
		eventList = new ArrayList<Event>();
		decisionTableMap = new LinkedHashMap<Vertex,DecisionTable>();
		// TODO work on ESG issuing IDs
		lastVertexID = -1;
		lastEdgeID = -1;
		lastEventID = -1;
		lastDecisionTableID = -1;
		lastSubEsgID = -1;
		// TODO labelList = new private ArrayList<Label>();
		vertexMap = new HashMap<Vertex, Set<Vertex>>();
		entryVertexSet = new LinkedHashSet<Vertex>();
		exitVertexSet = new LinkedHashSet<Vertex>();
	}

	public ESG(ESG esg) {
		this.ID = esg.getID();
		this.name = esg.getName();
		vertexList = new ArrayList<Vertex>();
		for (Vertex vertex : esg.getVertexList())
			vertexList.add(vertex);
		edgeList = new ArrayList<Edge>();
		for (Edge edge : esg.getEdgeList())
			edgeList.add(edge);
		eventList = new ArrayList<Event>(esg.getEventList());
		for (Event event : esg.getEventList())
			eventList.add(event);
		decisionTableMap = new LinkedHashMap<Vertex,DecisionTable>();
		for (Entry<Vertex,DecisionTable> entry : esg.getDecisionTableMap().entrySet())
			decisionTableMap.put(entry.getKey(), entry.getValue());
		lastVertexID = esg.getLastVertexID();
		lastEdgeID = esg.getLastEdgeID();
		lastEventID = esg.getLastEventID();
		lastDecisionTableID = esg.getLastDecisionTableID();
		lastSubEsgID = esg.getLastSubEsgID();
		vertexMap = new HashMap<Vertex, Set<Vertex>>();
		Set<Vertex> keySet = esg.getVertexMap().keySet();
		for (Vertex key : keySet) {
			Set<Vertex> targetSet = new LinkedHashSet<Vertex>();
			for (Vertex value : esg.getVertexMap().get(key))
				targetSet.add(value);
			vertexMap.put(key, targetSet);
		}
		entryVertexSet = new LinkedHashSet<Vertex>();
		for (Vertex vertex : esg.getEntryVertexSet())
			entryVertexSet.add(vertex);
		exitVertexSet = new LinkedHashSet<Vertex>();
		for (Vertex vertex : esg.getExitVertexSet())
			exitVertexSet.add(vertex);
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public List<Vertex> getVertexList() {
		return vertexList;
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public Map<Vertex,DecisionTable> getDecisionTableMap() {
		return decisionTableMap;
	}

	public Map<Vertex, Set<Vertex>> getVertexMap() {
		return vertexMap;
	}

	public Vertex getVertexByID(int ID) {
		for (Vertex vertex : vertexList) {
			if (ID == vertex.getID())
				return vertex;
		}
		return null;
	}
	
	public Vertex getVertexByEventName(String eventName) {
		for (Vertex vertex : vertexList) {
			if (vertex.getEvent().getName().equals(eventName))
				return vertex;
		}
		return null;
		
	}

	public Vertex getPseudoStartVertex() {
		for (Vertex vertex : vertexList) {
			if (vertex.isPseudoStartVertex())
				return vertex;
		}
		return null;
	}

	public Vertex getPseudoEndVertex() {
		for (Vertex vertex : vertexList) {
			if (vertex.isPseudoEndVertex())
				return vertex;
		}
		return null;
	}

	public Set<Vertex> getEntryVertexSet() {
		return entryVertexSet;
	}

	public Set<Vertex> getExitVertexSet() {
		return exitVertexSet;
	}

	public List<Vertex> getRealVertexList() {
		List<Vertex> realVertexList = new ArrayList<Vertex>();

		for (Vertex vertex : vertexList) {

			if (vertex.isPseudoEndVertex() || vertex.isPseudoStartVertex()) {
				continue;
			} else {
				realVertexList.add(vertex);
			}
		}
		return realVertexList;
	}

	public List<Edge> getRealEdgeList() {
		List<Edge> realEdgeList = new ArrayList<Edge>();

		for (Edge edge : edgeList) {

			if (edge.getSource().isPseudoStartVertex() || edge.getTarget().isPseudoEndVertex()) {
				continue;
			} else {
				realEdgeList.add(edge);
			}
		}
		return realEdgeList;
	}

	public void addVertex(Vertex vertex) {
		vertexList.add(vertex);
	}

	public void removeVertex(Vertex vertex) {
		vertexList.remove(vertex);
	}

	public void addEdge(Edge edge) {
		edgeList.add(edge);
		Set<Vertex> targetSet = null;

		edge.getSource().outDegree();
		edge.getTarget().inDegree();

		if (edge.getSource().isPseudoStartVertex()) {
			entryVertexSet.add(edge.getTarget());
		}

		if (edge.getTarget().isPseudoEndVertex()) {
			exitVertexSet.add(edge.getSource());
		}

		if (vertexMap.containsKey(edge.getSource())) {
			targetSet = vertexMap.get(edge.getSource());
		} else {
			targetSet = new LinkedHashSet<Vertex>();
		}

		// TODO encapsulate the following in try-catch block
		// in case try to add existing Target Vertex
		if (!edge.getSource().equals(edge.getTarget()))
			targetSet.add(edge.getTarget());

		vertexMap.put(edge.getSource(), targetSet);
	}

	public void removeEdge(Edge edge) {

		edgeList.remove(edge);
		Set<Vertex> targetSet = null;

		edge.getSource().inDegree();

		edge.getTarget().outDegree();

		if (edge.getSource().isPseudoStartVertex()) {
			entryVertexSet.remove(edge.getTarget());
		}

		if (edge.getTarget().isPseudoEndVertex()) {
			exitVertexSet.remove(edge.getSource());
		}

		if (vertexMap.containsKey(edge.getSource())) {
			targetSet = vertexMap.get(edge.getSource());
			if (targetSet.size() == 1) {
				vertexMap.remove(edge.getSource(), targetSet);
			} else {
				targetSet.remove(edge.getTarget());

			}

		} else {
			vertexMap.remove(edge.getSource());
		}

	}

	public void addEvent(Event event) {
		eventList.add(event);
	}

	public void removeEvent(Event event) {
		eventList.remove(event);
	}

	public void addDecisionTable(Vertex vertex, DecisionTable decisionTable) {
		decisionTableMap.put(vertex,decisionTable);
	}

	public void removeDecisionTable(Vertex vertex,DecisionTable decisionTable) {
		decisionTableMap.remove(vertex, decisionTable);
	}

	public void addVertexList(List<Vertex> vertexList) {
		for (Vertex vertex : vertexList) {
			addVertex(vertex);
		}
	}


	public void addEdgeList(List<Edge> edgeList) {
		for (Edge edge : edgeList) {
			addEdge(edge);
		}
	}

	public void addEventList(List<Event> eventList) {
		for (Event event : eventList) {
			addEvent(event);
		}
	}


	private String vertexListToString() {
		String vertexListToString = "Vertex List as (ID)Event: \n";
		for (Vertex vertex : vertexList)
			vertexListToString += "(" + vertex.getID() + ")" + vertex.getEvent().getName() + ", ";
		vertexListToString += "\n";
		return vertexListToString;
	}

	private String edgeListToString() {
		String edgeListToString = "Edge List as (ID): \n";
		for (Edge edge : edgeList)
			edgeListToString += "(" + edge.getID() + ")" + "<" + edge.getSource().getEvent().getName() + "-"
					+ edge.getTarget().getEvent().getName() + ">" + ", ";
		edgeListToString += "\n";
		return edgeListToString;
	}

	private String vertexMapToString() {
		Set<Vertex> keySet = vertexMap.keySet();
		String edgeMapToString = "Vertex Map as <(ID)Event, (ID)Event>:\n";
		for (Vertex key : keySet) {
			for (Vertex value : vertexMap.get(key)) {
				edgeMapToString += "<" + "(" + key.getID() + ")" + key.getEvent().getName() + ", " + "(" + value.getID()
						+ ")" + value.getEvent().getName() + ">\n";
			}
		}
		edgeMapToString += "\n";
		return edgeMapToString;
	}

	public int getLastVertexID() {
		return lastVertexID;
	}

	public int getLastEdgeID() {
		return lastEdgeID;
	}

	public int getLastEventID() {
		return lastEventID;
	}

	public int getLastDecisionTableID() {
		return lastDecisionTableID;
	}

	public int getLastSubEsgID() {
		return lastSubEsgID;
	}

	public int getNextVertexID() {
		lastVertexID++;
		return lastVertexID;
	}

	public int getNextEdgeID() {
		lastEdgeID++;
		return lastEdgeID;
	}

	public int getNextEventID() {
		lastEventID++;
		return lastEventID;
	}

	public int getNextDecisionTableID() {
		lastDecisionTableID++;
		return lastDecisionTableID;
	}

	public int getNextSubEsgID() {
		lastSubEsgID++;
		return lastSubEsgID;
	}

	@Override
	public String toString() {
		String toString = "ESG " + ID + ", " + name + "\n";
		toString += vertexListToString();
		toString += edgeListToString();
		toString += vertexMapToString();
		return toString;
	}
}
