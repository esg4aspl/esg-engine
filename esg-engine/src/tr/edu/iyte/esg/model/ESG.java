package tr.edu.iyte.esg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tr.edu.iyte.esg.model.decisiontable.DecisionTable;

public class ESG {

	protected int ID;
	final private String name;
	private int lastVertexID, lastEdgeID, lastEventID, lastDecisionTableID, lastSubEsgID;

	private Map<Integer, Vertex> vertexIDMap;
	private Map<Integer, Edge> edgeIDMap;
	private Map<Integer, Event> eventIDMap;

	private Map<String, Vertex> vertexNameMap;
	private Map<String, Edge> edgeNameMap;
	private Map<String, Event> eventNameMap;

	private Map<Vertex, DecisionTable> decisionTableMap;

	private Set<Vertex> entryVertexSet;
	private Set<Vertex> exitVertexSet;

	private Map<Vertex, Set<Vertex>> vertexMap;

	public ESG(int ID, String name) {
		this.ID = ID;
		this.name = name;

		vertexIDMap = new LinkedHashMap<>();
		edgeIDMap = new LinkedHashMap<>();
		eventIDMap = new LinkedHashMap<>();

		decisionTableMap = new LinkedHashMap<Vertex, DecisionTable>();

		lastVertexID = -1;
		lastEdgeID = -1;
		lastEventID = -1;
		lastDecisionTableID = -1;
		lastSubEsgID = -1;

		vertexMap = new HashMap<Vertex, Set<Vertex>>();
		entryVertexSet = new LinkedHashSet<Vertex>();
		exitVertexSet = new LinkedHashSet<Vertex>();

		vertexNameMap = new HashMap<>();
		edgeNameMap = new HashMap<>();
		eventNameMap = new HashMap<>();
	}

	public ESG(ESG esg) {
		this.ID = esg.getID();
		this.name = esg.getName();

		vertexIDMap = new LinkedHashMap<>(esg.vertexIDMap);
		edgeIDMap = new LinkedHashMap<>(esg.edgeIDMap);
		eventIDMap = new LinkedHashMap<>(esg.eventIDMap);

		entryVertexSet = new LinkedHashSet<>(esg.getEntryVertexSet());
		exitVertexSet = new LinkedHashSet<>(esg.getExitVertexSet());

		decisionTableMap = new LinkedHashMap<>(esg.getDecisionTableMap());

		lastVertexID = esg.getLastVertexID();
		lastEdgeID = esg.getLastEdgeID();
		lastEventID = esg.getLastEventID();
		lastDecisionTableID = esg.getLastDecisionTableID();
		lastSubEsgID = esg.getLastSubEsgID();

		// Deep copy vertexMap
		vertexMap = new LinkedHashMap<>();
		for (Map.Entry<Vertex, Set<Vertex>> entry : esg.getVertexMap().entrySet()) {
			vertexMap.put(entry.getKey(), new LinkedHashSet<>(entry.getValue()));
		}

		vertexNameMap = new HashMap<>(esg.vertexNameMap);
		edgeNameMap = new HashMap<>(esg.edgeNameMap);
		eventNameMap = new HashMap<>(esg.eventNameMap);
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public List<Vertex> getVertexList() {
		return new ArrayList<>(vertexIDMap.values());
	}

	public List<Edge> getEdgeList() {
		return new ArrayList<>(edgeIDMap.values());
	}

	public List<Event> getEventList() {
		return new ArrayList<>(eventIDMap.values());
	}

	public Map<Vertex, DecisionTable> getDecisionTableMap() {
		return decisionTableMap;
	}

	public Map<Vertex, Set<Vertex>> getVertexMap() {
		return vertexMap;
	}

	public Vertex getVertexByID(int ID) {
		return vertexIDMap.get(ID);
	}

	public Vertex getVertexByEventName(String eventName) {
		return vertexNameMap.get(eventName);
	}
	
	public Event getEventByID(int ID) {
		return eventIDMap.get(ID);
	}
	
	public Event getEventByEventName(String eventName) {
		return eventNameMap.get(eventName);
	}
	
	public Edge getEdgeByID(int ID) {
		return edgeIDMap.get(ID);
	}
	
	public Edge getEdgeBySourceEventNameTargetEventName(String source, String target) {
		String key = source + "|" + target;
		return edgeNameMap.get(key);
	}
	
	// Get all edges connected to a vertex by event name (either as source or target)
	public Set<Edge> getEdgesByEventName(String eventName) {
		Set<Edge> edgeSet = new LinkedHashSet<>();

		Vertex vertex = vertexNameMap.get(eventName);

		// If the vertex does not exist, return the empty set immediately
		if (vertex == null) {
			return edgeSet;
		}

		for (Edge edge : edgeIDMap.values()) {
			if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
				edgeSet.add(edge);
			}
		}
		
		return edgeSet;
	}

	public Vertex getPseudoStartVertex() {
		for (Vertex vertex : vertexIDMap.values()) {
			if (vertex.isPseudoStartVertex())
				return vertex;
		}
		return null;
	}

	public Vertex getPseudoEndVertex() {
		for (Vertex vertex : vertexIDMap.values()) {
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

		for (Vertex vertex : vertexIDMap.values()) {
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

		for (Edge edge : edgeIDMap.values()) {
			if (edge.getSource().isPseudoStartVertex() || edge.getTarget().isPseudoEndVertex()) {
				continue;
			} else {
				realEdgeList.add(edge);
			}
		}
		return realEdgeList;
	}

	public void addVertex(Vertex vertex) {

		int vertexID = vertex.getID();
		int eventID = vertex.getEvent().getID();
		Event event = vertex.getEvent();
		String eventName = event.getName();

		vertexIDMap.put(vertexID, vertex);
		vertexNameMap.put(eventName, vertex);

		if (!eventIDMap.containsKey(eventID) || !eventNameMap.containsKey(eventName)) {
			addEvent(event);
		}
	}

	public void removeVertex(Vertex vertex) {
		int vertexID = vertex.getID();
		int eventID = vertex.getEvent().getID();
		Event event = vertex.getEvent();
		String eventName = event.getName();

		vertexIDMap.remove(vertexID);
		vertexNameMap.remove(eventName);

		if (eventIDMap.containsKey(eventID) || eventNameMap.containsKey(eventName)) {
			removeEvent(event);
		}

	}

	public void addEvent(Event event) {
		int eventID = event.getID();
		String eventName = event.getName();
		eventIDMap.put(eventID, event);
		eventNameMap.put(eventName, event);

	}

	public void removeEvent(Event event) {
		int eventID = event.getID();
		String eventName = event.getName();

		eventIDMap.remove(eventID);
		eventNameMap.remove(eventName);
	}

	public void addEdge(Edge edge) {

		edgeIDMap.put(edge.getID(), edge);

		String key = edge.toString();
		edgeNameMap.put(key, edge);

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

		if (!edge.getSource().equals(edge.getTarget()))
			targetSet.add(edge.getTarget());

		vertexMap.put(edge.getSource(), targetSet);
	}

	public void removeEdge(Edge edge) {

		edgeIDMap.remove(edge.getID());

		String key = edge.toString();
		edgeNameMap.remove(key);

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

			targetSet.remove(edge.getTarget());

			if (targetSet.isEmpty()) {
				vertexMap.remove(edge.getSource());
			}
		} else {
			vertexMap.remove(edge.getSource());
		}
	}

	public void addDecisionTable(Vertex vertex, DecisionTable decisionTable) {
		decisionTableMap.put(vertex, decisionTable);
	}

	public void removeDecisionTable(Vertex vertex, DecisionTable decisionTable) {
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
		StringBuilder sb = new StringBuilder("Vertex List as (ID)Event: \n");
		for (Vertex vertex : vertexIDMap.values()) {
			sb.append("(").append(vertex.getID()).append(")").append(vertex.getEvent().getName()).append(", ");
		}
		sb.append("\n");
		return sb.toString();
	}

	private String edgeListToString() {
		StringBuilder sb = new StringBuilder("Edge List as (ID): \n");
		for (Edge edge : edgeIDMap.values()) {
			sb.append("(").append(edge.getID()).append(")<").append(edge.getSource().getEvent().getName()).append("-")
					.append(edge.getTarget().getEvent().getName()).append(">, ");
		}
		sb.append("\n");
		return sb.toString();
	}

	private String vertexMapToString() {
		StringBuilder sb = new StringBuilder("Vertex Map as <(ID)Event, (ID)Event>:\n");
		for (Vertex key : vertexMap.keySet()) {
			for (Vertex value : vertexMap.get(key)) {
				sb.append("<(").append(key.getID()).append(")").append(key.getEvent().getName()).append(", (")
						.append(value.getID()).append(")").append(value.getEvent().getName()).append(">\n");
			}
		}
		sb.append("\n");
		return sb.toString();
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
		return ++lastVertexID;
	}

	public int getNextEdgeID() {
		return ++lastEdgeID;
	}

	public int getNextEventID() {
		return ++lastEventID;
	}

	public int getNextDecisionTableID() {
		return ++lastDecisionTableID;
	}

	public int getNextSubEsgID() {
		return ++lastSubEsgID;
	}

	@Override
	public String toString() {
		return "ESG " + ID + ", " + name + "\n" + vertexListToString() + edgeListToString() + vertexMapToString();
	}
}