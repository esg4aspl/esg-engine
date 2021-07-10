package tr.edu.iyte.esg.eventsequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Vertex;

public class EventSequence {

	/**
	 * Represents the event sequence vertices are held in a list since they are
	 * sequential in event sequence
	 */
	private List<Vertex> eventSequenceList;

	/**
	 * Constructor
	 */
	public EventSequence() {
		setEventSequence(new LinkedList<Vertex>());
	}

	public List<Vertex> getEventSequence() {
		return eventSequenceList;
	}

	public void setEventSequence(List<Vertex> eventSequence) {
		this.eventSequenceList = eventSequence;
	}

	/**
	 * Returns the initial event of the event sequence, it is not the pseudo initial vertex
	 * "["
	 * 
	 * @return Vertex
	 */
	public Vertex getStartVertex() {

		return eventSequenceList.get(0);
	}

	/**
	 * Returns the end event of event sequence, it is not the pseudo end vertex "]"
	 * 
	 * @return end event that is held in Vertex
	 */
	public Vertex getEndVertex() {
		return eventSequenceList.get(eventSequenceList.size() - 1);
	}

	/**
	 * Returns a list of Vertex that represents the predecessors of a given Vertex
	 * in the event sequence Vertex object contains Event
	 * 
	 * @param event that is held in {@link Vertex}
	 * @return list of Vertex
	 */
	public List<Vertex> predecessors(Vertex event) {
		List<Vertex> predessors = new LinkedList<Vertex>();
		int indexOfEvent = eventSequenceList.indexOf(event);

		for (int i = 0; i < indexOfEvent; i++) {
			predessors.add(eventSequenceList.get(i));
		}
		return predessors;
	}

	/**
	 * Returns a list of Vertex that represents the successors of a given Vertex in
	 * the event sequence Vertex object contains Event
	 * 
	 * @param event that is held in {@link Vertex}}
	 * @return list of Vertex
	 */
	public List<Vertex> successors(Vertex event) {
		List<Vertex> successorList = new LinkedList<Vertex>();
		int indexOfEvent = eventSequenceList.indexOf(event);

		if (indexOfEvent != eventSequenceList.size() - 1) {
			for (int i = indexOfEvent + 1; i < eventSequenceList.size(); i++) {
				successorList.add(eventSequenceList.get(i));
			}
		}
		return successorList;
	}

	/**
	 * Returns the length of the event sequence
	 * 
	 * @return <code>int</code>
	 */
	public int length() {
		return eventSequenceList.size();
	}

	/**
	 * Returns <code>true</code> if the event sequence is complete
	 * 
	 * @param ESG which event sequence belongs to
	 * @return <code>boolean</code>
	 */
	public boolean isCompleteEventSequence(ESG aESG) {
		return aESG.getEntryVertexSet().contains(getStartVertex())
				&& aESG.getExitVertexSet().contains(getEndVertex());
	}

	/**
	 * Returns true if the event sequence has the length n
	 * 
	 * @return <code>boolean</code>
	 */
	public boolean isEventTuple(int n) {
		return length() == n;
	}
	
	public List<Vertex> getSubsequence(int start, int end) {
		List<Vertex> list = null;
		if(start<=end && start>=0 && end<eventSequenceList.size()) {
			list = new ArrayList<Vertex>();
			Iterator<Vertex> itr = eventSequenceList.listIterator(start);
			for (int i = start; i <= end; i++) {
				list.add(itr.next());
			}
		}
		return list;
	}

//	@Override
//	public String toString() {
//		String es = "";
//
//		for (Vertex vertex : eventSequenceList) {
//			es += vertex.toString().trim() + ", ";
//		}
//
//		return es;
//	}
	
	public boolean hasSubsequence(EventSequence subsequence) {
		
		String eventSequence = this.toString();
		
		String subSequence = subsequence.toString();
		
		
		
		return eventSequence.contains(subSequence);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(eventSequenceList.size() + " : ");
		
		for(int i= 0; i < eventSequenceList.size(); i++) {
			sb.append(eventSequenceList.get(i).toString().trim());
			
			if(i != eventSequenceList.size() -1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return toString().equals(o.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	

}
