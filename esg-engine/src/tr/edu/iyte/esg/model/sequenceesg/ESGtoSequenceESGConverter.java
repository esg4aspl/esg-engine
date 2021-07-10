package tr.edu.iyte.esg.model.sequenceesg;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedBySequence;
import tr.edu.iyte.esg.model.comparators.SequenceComparator;
import tr.edu.iyte.esg.model.comparators.VertexComparator;

public class ESGtoSequenceESGConverter {
	
	public ESGtoSequenceESGConverter() {
		
	}
	
	/**
	 - ID and name for ESG
	   -- ID of the sequence ESG is obtained by incrementing the ID of the input ESG.
	   -- Name of the (sequence ESG is obtained by appending "s" to the name of the input ESG.
	 - Events in the event list
	   -- Event list of the sequence ESG contains the event instances in the event list of the input ESG.
	 - Vertices in sequences in sequence vertices
	   -- Sequence ESG uses existing vertex instances from the input ESG to construct sequences and sequence vertices.
	 - Avoiding the creation of redundant sequence vertices
	   -- A new sequence vertex instance is not created if there is a sequence vertex instance containing an equivalent sequence.
	   -- Instances are looked up from the sequence vertex list with respect to the sequences.
	 - A sequence vertex, its event and its vertex sequence
	   -- An event of a sequence vertex is not an actual event; therefore, it is not included in the event list.
	   -- Name of a sequence vertex event is the string form of the sequence constructed using contexted string forms of the events in the vertices of the sequence.
	 */
	public ESG convert(ESG esg) {
		ESG sesg = new ESG(esg.getID()+1, esg.getName()+"s"); //!!! esg id and name
		for(Event e : esg.getEventList()) { //!!! actual events
			sesg.addEvent(e);
		}
		for(Vertex v : esg.getVertexList()) {
			Sequence<Vertex> s = new Sequence<Vertex>();
			s.addElement(v); //!!! existing vertex instances
			Event e = new EventSimple(sesg.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(s)); //!!!name for the event of the sequence vertex
			VertexRefinedBySequence sv = new VertexRefinedBySequence(sesg.getNextVertexID(), e, s);
			sesg.addVertex(sv);
		}
		VertexComparator vc = new VertexComparator();
		SequenceComparator<Vertex> sc = new SequenceComparator<Vertex>(vc);
		for(Edge e : esg.getEdgeList()) {
			Sequence<Vertex> s = new Sequence<Vertex>();
			s.addElement(e.getSource());
			VertexRefinedBySequence v = SequenceESGUtilities.getVertexByVertexSequence(sesg, s, sc); //!!! look up to avoid using redundant instances (performance decrease)
			
			Sequence<Vertex> t = new Sequence<Vertex>();
			t.addElement(e.getTarget());
			VertexRefinedBySequence w = SequenceESGUtilities.getVertexByVertexSequence(sesg, t, sc); //!!! look up to avoid using redundant instances (performance decrease)
			
			sesg.addEdge(new EdgeSimple(sesg.getNextEdgeID(), v, w));
		}
		
		
		return sesg;
	}
}
